package dpla.ingestion3.harvesters.api

// Represents a single page response from an API request
sealed trait ApiResponse

/** @param queryParams
  * @param url
  * @param text
  */
case class ApiSource(
    queryParams: Map[String, String],
    url: Option[String] = None,
    text: Option[String] = None
) extends ApiResponse

/** @param message
  * @param errorSource
  */
case class ApiError(message: String, errorSource: ApiSource) extends ApiResponse

/** @param id
  * @param document
  */
case class ApiRecord(id: String, document: String) extends ApiResponse
