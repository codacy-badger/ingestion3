package dpla.ingestion3.harvesters.file

import java.io.{BufferedReader, File, FileInputStream}
import java.util.zip.GZIPInputStream
import dpla.ingestion3.confs.i3Conf
import dpla.ingestion3.harvesters.file.FileFilters.GzFileFilter
import dpla.ingestion3.harvesters.{AvroHelper, Harvester}
import dpla.ingestion3.model.AVRO_MIME_XML
import dpla.ingestion3.utils.{FlatFileIO, Utils}
import org.apache.avro.Schema
import org.apache.avro.file.DataFileWriter
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.commons.io.{FileUtils, IOUtils}
import org.apache.hadoop.fs.Path
import org.apache.log4j.Logger
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.tools.bzip2.CBZip2InputStream
import org.apache.tools.tar.TarInputStream

import scala.util.{Failure, Success, Try}
import scala.xml._

class NaraDeltaHarvester(
    spark: SparkSession,
    shortName: String,
    conf: i3Conf,
    logger: Logger
) extends Harvester(spark, shortName, conf, logger) {

  /** Case class hold the parsed value from a given FileResult
    */
  case class ParsedResult(id: String, item: String)

  /** Case class to hold the results of a file
    *
    * @param entryName
    *   Path of the entry in the file
    * @param data
    *   Holds the data for the entry, or None if it's a directory.
    * @param bufferedData
    *   Holds a buffered reader for the entry if it's too large to be held in
    *   memory.
    */
  case class FileResult(
      entryName: String,
      data: Option[Array[Byte]],
      bufferedData: Option[BufferedReader] = None
  )

  lazy val naraSchema: Schema =
    new Schema.Parser()
      .parse(new FlatFileIO().readFileAsString("/avro/OriginalRecord.avsc"))

  val avroWriterNara: DataFileWriter[GenericRecord] =
    AvroHelper.avroWriter(shortName, naraTmp, naraSchema)

  // Temporary output path.
  lazy val naraTmp: String =
    new File(FileUtils.getTempDirectory, shortName).getAbsolutePath

  def mimeType: GenericData.EnumSymbol = AVRO_MIME_XML

  /** Loads .gz, .tgz, .bz, and .tbz2, and plain old .tar files.
    *
    * @param file
    *   File to parse
    * @return
    *   TarInputstream of the tar contents
    */
  def getInputStream(file: File): Option[TarInputStream] =
    file.getName match {
      case gzName if gzName.endsWith("gz") || gzName.endsWith("tgz") =>
        Some(new TarInputStream(new GZIPInputStream(new FileInputStream(file))))

      case bz2name if bz2name.endsWith("bz2") || bz2name.endsWith("tbz2") =>
        val inputStream = new FileInputStream(file)
        inputStream.skip(2)
        Some(new TarInputStream(new CBZip2InputStream(inputStream)))

      case tarName if tarName.endsWith("tar") =>
        Some(new TarInputStream(new FileInputStream(file)))

      case _ => None
    }

  /** Takes care of parsing an xml file into a list of Nodes each representing
    * an item
    *
    * @param xml
    *   Root of the xml document
    * @return
    *   List of Options of id/item pairs.
    */
  def handleXML(xml: Node): List[Option[ParsedResult]] = {
    for {
      // three different types of nodes contain children that represent records
      items <- xml \\ "item" :: xml \\ "itemAv" :: xml \\ "fileUnit" :: Nil
      item <- items
      if (item \ "digitalObjectArray" \ "digitalObject").nonEmpty
    } yield item match {
      case record: Node =>
        val id = (record \ "naId").text
        val outputXML = xmlToString(record)
        val label = item.label
        Some(ParsedResult(id, outputXML))
      case _ =>
        logger.warn("Got weird result back for item path: " + item.getClass)
        None
    }
  }

  /** Main logic for handling individual entries in the tar.
    *
    * @param tarResult
    *   Case class representing extracted item from the tar
    * @return
    *   Count of metadata items found.
    */
  def handleFile(
      tarResult: FileResult,
      unixEpoch: Long,
      filename: String
  ): Try[Int] =
    tarResult.data match {
      case None =>
        Success(0) // a directory, no results

      case Some(data) =>
        Try {
          val dataString = new String(data).replaceAll("<\\?xml.*\\?>", "").trim
          val xml = XML.loadString(dataString)

          val items = handleXML(xml)

          val counts = for {
            itemOption <- items
            item <- itemOption // filters out the Nones
          } yield {
            writeOutNara(unixEpoch, item, filename)
            1
          }
          counts.sum
        }
    }

  def getAvroWriterNara: DataFileWriter[GenericRecord] = avroWriterNara

  /** Writes item out
    *
    * @param unixEpoch
    *   Timestamp of the harvest
    * @param item
    *   Harvested record
    */
  def writeOutNara(unixEpoch: Long, item: ParsedResult, file: String): Unit = {
    val avroWriter = getAvroWriterNara
    val schema = naraSchema

    val genericRecord = new GenericData.Record(schema)
    genericRecord.put("id", item.id)
    genericRecord.put("ingestDate", unixEpoch)
    genericRecord.put("provider", shortName)
    genericRecord.put("document", item.item)
    genericRecord.put("mimetype", mimeType)

    avroWriter.append(genericRecord)

  }

  /** Implements a stream of files from the tar. Can't use @tailrec here because
    * the compiler can't recognize it as tail recursive, but this won't blow the
    * stack.
    *
    * @param tarInputStream
    * @return
    *   Lazy stream of tar records
    */
  def iter(tarInputStream: TarInputStream): Stream[FileResult] =
    Option(tarInputStream.getNextEntry) match {
      case None =>
        Stream.empty

      case Some(entry) =>
        val filename = Try {
          entry.getName
        }.getOrElse("")

        val result =
          if (
            entry.isDirectory || filename.contains("._")
          ) // drop OSX hidden files
            None
          else if (filename.endsWith(".xml")) // only read xml files
            Some(IOUtils.toByteArray(tarInputStream, entry.getSize))
          else
            None

        FileResult(entry.getName, result) #:: iter(tarInputStream)
    }

  /** Executes the nara harvest
    */
  def localHarvest(): DataFrame = {
    val harvestTime = System.currentTimeMillis()
    val unixEpoch = harvestTime / 1000L

    // Path to the incremental update file [*.tar.gz without deletes]
    val deltaIn = conf.harvest.update.getOrElse("in")
    val deltaHarvestInFile = new File(deltaIn)

    if (deltaHarvestInFile.isDirectory)
      for (
        file: File <- deltaHarvestInFile.listFiles(new GzFileFilter).sorted
      ) {
        logger.info(s"Harvesting NARA delta changes from ${file.getName}")
        harvestFile(file, unixEpoch)
      }
    else
      harvestFile(deltaHarvestInFile, unixEpoch)

    // flush writes
    avroWriterNara.flush()

    // Get the absolute path of the avro file written to naraTmp directory
    val naraTempFile = new File(naraTmp)
      .listFiles(new AvroFileFilter)
      .headOption
      .getOrElse(
        throw new RuntimeException(
          s"Unable to load avro file in $naraTmp directory. Unable to continue"
        )
      )
      .getAbsolutePath

    val localSrcPath = new Path(naraTempFile)
    val dfDeltaRecords = spark.read.format("avro").load(localSrcPath.toString)

    dfDeltaRecords
  }

  override def cleanUp(): Unit = {
    logger.info(s"Cleaning up $naraTmp directory and files")
    avroWriterNara.flush()
    avroWriterNara.close()
    // Delete temporary output directory and files.
    Utils.deleteRecursively(new File(naraTmp))
  }

  private def harvestFile(file: File, unixEpoch: Long): Unit = {
    val inputStream = getInputStream(file)
      .getOrElse(
        throw new IllegalArgumentException(
          s"Couldn't load tar file: ${file.getAbsolutePath}"
        )
      )

    val recordCount = (for (tarResult <- iter(inputStream)) yield {
      handleFile(tarResult, unixEpoch, file.getName) match {
        case Failure(exception) =>
          logger
            .error(s"Caught exception on ${tarResult.entryName}.", exception)
          0
        case Success(count) =>
          count
      }
    }).sum

    logger.info(s"Harvested $recordCount records from ${file.getName}")

    IOUtils.closeQuietly(inputStream)
  }

  /** Converts a Node to an xml string
    *
    * @param node
    *   The root of the tree to write to a string
    * @return
    *   a String containing xml
    */
  def xmlToString(node: Node): String =
    Utility.serialize(node, minimizeTags = MinimizeMode.Always).toString
}
