package dpla.ingestion3.mappers.rdf

import org.eclipse.rdf4j.model.impl.{SimpleIRI, SimpleNamespace}
import org.eclipse.rdf4j.model.{IRI, Namespace}

/**
  * An abstract class that represents a suite of IRIs for an ontology. Similar to the classes in
  * org.eclipse.rdf4j.model.vocabulary, but tidier given the scala syntax.

 */
abstract class Vocabulary(nsPrefix: String, nsUri: String) extends RdfValueUtils {

  def ns: Namespace = new SimpleNamespace(nsPrefix, nsUri)

  def apply(name: String): IRI = iri(nsUri, name)
}

case class RDF() extends Vocabulary("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#") {

  import org.eclipse.rdf4j.model.vocabulary.RDF._

  val `type` = TYPE
  val property = PROPERTY
  val XMLLiteral = XMLLITERAL
  val subject = SUBJECT
  val predicate = PREDICATE
  val `object` = OBJECT
  val Statement = STATEMENT
  val Bag = BAG
  val Alt = ALT
  val Seq = SEQ
  val value = VALUE
  val li = LI
  val List = LIST
  val first = FIRST
  val rest = REST
  val nil = NIL
  val langString = LANGSTRING
  val HTML = org.eclipse.rdf4j.model.vocabulary.RDF.HTML
}

case class DPLA() extends Vocabulary("dpla", "http://dp.la/about/map/") {
  val Place: IRI = apply("Place")
  val SourceResource: IRI = apply("SourceResource")
  val intermediateProvider: IRI = apply("intermediateProvider")
  val originalRecord: IRI = apply("originalRecord")
  val isReplacedBy: IRI = apply("isReplacedBy")
  val providedLabel: IRI = apply("providedLabel")
  val replaces: IRI = apply("replaces")
}

case class CNT()
  extends Vocabulary("cnt", "http://www.w3.org/2011/content#") {
  val Content: IRI = apply("#Content")
  val ContentAsBase64: IRI = apply("ContentAsBase64")
  val ContentAsText: IRI = apply("ContentAsText")
  val ContentAsXML: IRI = apply("ContentAsXML")
  val DoctypeDecl: IRI = apply("DoctypeDecl")
  val bytes: IRI = apply("bytes")
  val characterEncoding: IRI = apply("characterEncoding")
  val chars: IRI = apply("chars")
  val declaredEncoding: IRI = apply("declaredEncoding")
  val doctypeName: IRI = apply("doctypeName")
  val dtDecl: IRI = apply("dtDecl")
  val internalSubset: IRI = apply("internalSubset")
  val leadingMisc: IRI = apply("leadingMisc")
  val publicId: IRI = apply("publicId")
  val rest: IRI = apply("rest")
  val standalone: IRI = apply("standalone")
  val systemId: IRI = apply("systemId")
  val version: IRI = apply("version")
}

case class DC()
  extends Vocabulary("dc", "http://purl.org/dc/elements/1.1/") {
  val contributor: IRI = apply("contributor")
  val coverage: IRI = apply("coverage")
  val creator: IRI = apply("creator")
  val date: IRI = apply("date")
  val description: IRI = apply("description")
  val format: IRI = apply("format")
  val identifier: IRI = apply("identifier")
  val language: IRI = apply("language")
  val publisher: IRI = apply("publisher")
  val relation: IRI = apply("relation")
  val rights: IRI = apply("rights")
  val source: IRI = apply("source")
  val subject: IRI = apply("subject")
  val title: IRI = apply("title")
  val `type`: IRI = apply("type")
}

case class DCTerms()
  extends Vocabulary("dcterms", "http://purl.org/dc/terms/") {
  val `abstract`: IRI = apply("abstract")
  val accessRights: IRI = apply("accessRights")
  val accrualMethod: IRI = apply("accrualMethod")
  val accrualPeriodicity: IRI = apply("accrualPeriodicity")
  val accrualPolicy: IRI = apply("accrualPolicy")
  val alternative: IRI = apply("alternative")
  val audience: IRI = apply("audience")
  val available: IRI = apply("available")
  val bibliographicCitation: IRI = apply("bibliographicCitation")
  val conformsTo: IRI = apply("conformsTo")
  val contributor: IRI = apply("contributor")
  val coverage: IRI = apply("coverage")
  val created: IRI = apply("created")
  val creator: IRI = apply("creator")
  val date: IRI = apply("date")
  val dateAccepted: IRI = apply("dateAccepted")
  val dateCopyrighted: IRI = apply("dateCopyrighted")
  val dateSubmitted: IRI = apply("dateSubmitted")
  val description: IRI = apply("description")
  val educationLevel: IRI = apply("educationLevel")
  val extent: IRI = apply("extent")
  val format: IRI = apply("format")
  val hasFormat: IRI = apply("hasFormat")
  val hasPart: IRI = apply("hasPart")
  val hasVersion: IRI = apply("hasVersion")
  val identifier: IRI = apply("identifier")
  val instructionalMethod: IRI = apply("instructionalMethod")
  val isFormatOf: IRI = apply("isFormatOf")
  val isPartOf: IRI = apply("isPartOf")
  val isReferencedBy: IRI = apply("isReferencedBy")
  val isReplacedBy: IRI = apply("isReplacedBy")
  val isRequiredBy: IRI = apply("isRequiredBy")
  val issued: IRI = apply("issued")
  val isVersionOf: IRI = apply("isVersionOf")
  val language: IRI = apply("language")
  val license: IRI = apply("license")
  val mediator: IRI = apply("mediator")
  val medium: IRI = apply("medium")
  val modified: IRI = apply("modified")
  val provenance: IRI = apply("provenance")
  val publisher: IRI = apply("publisher")
  val references: IRI = apply("references")
  val relation: IRI = apply("relation")
  val replaces: IRI = apply("replaces")
  val requires: IRI = apply("requires")
  val rights: IRI = apply("rights")
  val rightsHolder: IRI = apply("rightsHolder")
  val source: IRI = apply("source")
  val spatial: IRI = apply("spatial")
  val subject: IRI = apply("subject")
  val tableOfContents: IRI = apply("tableOfContents")
  val temporal: IRI = apply("temporal")
  val title: IRI = apply("title")
  val `type`: IRI = apply("type")
  val valid: IRI = apply("valid")

  val Agent: IRI = apply("Agent")
  val AgentClass: IRI = apply("AgentClass")
  val BibliographicResource: IRI = apply("BibliographicResource")
  val FileFormat: IRI = apply("FileFormat")
  val Frequency: IRI = apply("Frequency")
  val Jurisdiction: IRI = apply("Jurisdiction")
  val LicenseDocument: IRI = apply("LicenseDocument")
  val LinguisticSystem: IRI = apply("LinguisticSystem")
  val Location: IRI = apply("Location")
  val LocationPeriodOrJurisdiction: IRI = apply("LocationPeriodOrJurisdiction")
  val MediaType: IRI = apply("MediaType")
  val MediaTypeOrExtent: IRI = apply("MediaTypeOrExtent")
  val MethodOfAccrual: IRI = apply("MethodOfAccrual")
  val MethodOfInstruction: IRI = apply("MethodOfInstruction")
  val PeriodOfTime: IRI = apply("PeriodOfTime")
  val PhysicalMedium: IRI = apply("PhysicalMedium")
  val PhysicalResource: IRI = apply("PhysicalResource")
  val Policy: IRI = apply("Policy")
  val ProvenanceStatement: IRI = apply("ProvenanceStatement")
  val RightsStatement: IRI = apply("RightsStatement")
  val SizeOrDuration: IRI = apply("SizeOrDuration")
  val Standard: IRI = apply("Standard")
}

case class DCMIType()
  extends Vocabulary("dcmitype", "http://purl.org/dc/dcmitype/") {
  val Collection: IRI = apply("Collection")
  val Dataset: IRI = apply("Dataset")
  val Event: IRI = apply("Event")
  val Image: IRI = apply("Image")
  val InteractiveResource: IRI = apply("InteractiveResource")
  val MovingImage: IRI = apply("MovingImage")
  val PhysicalObject: IRI = apply("PhysicalObject")
  val Service: IRI = apply("Service")
  val Software: IRI = apply("Software")
  val Sound: IRI = apply("Sound")
  val StillImage: IRI = apply("StillImage")
  val Text: IRI = apply("Text")
}

case class EDM()
  extends Vocabulary("edm", "http://www.europeana.eu/schemas/edm/") {
  val Agent: IRI = apply("Agent")
  val EuropeanaAggregation: IRI = apply("EuropeanaAggregation")
  val EuropeanaObject: IRI = apply("EuropeanaObject")
  val Event: IRI = apply("Event")
  val InformationResource: IRI = apply("InformationResource")
  val NonInformationResource: IRI = apply("NonInformationResource")
  val PhysicalThing: IRI = apply("PhysicalThing")
  val Place: IRI = apply("Place")
  val ProvidedCHO: IRI = apply("ProvidedCHO")
  val TimeSpan: IRI = apply("TimeSpan")
  val WebResource: IRI = apply("WebResource")
  val aggregatedCHO: IRI = apply("aggregatedCHO")
  val begin: IRI = apply("begin")
  val collectionName: IRI = apply("collectionName")
  val country: IRI = apply("country")
  val currentLocation: IRI = apply("currentLocation")
  val dataProvider: IRI = apply("dataProvider")
  val end: IRI = apply("end")
  val europeanaProxy: IRI = apply("europeanaProxy")
  val happenedAt: IRI = apply("happenedAt")
  val hasMet: IRI = apply("hasMet")
  val hasType: IRI = apply("hasType")
  val hasView: IRI = apply("hasView")
  val incorporates: IRI = apply("incorporates")
  val isAnnotationOf: IRI = apply("isAnnotationOf")
  val isDerivativeOf: IRI = apply("isDerivativeOf")
  val isNextInSequence: IRI = apply("isNextInSequence")
  val isRelatedTo: IRI = apply("isRelatedTo")
  val isRepresentationOf: IRI = apply("isRepresentationOf")
  val isShownAt: IRI = apply("isShownAt")
  val isShownBy: IRI = apply("isShownBy")
  val isSimilarTo: IRI = apply("isSimilarTo")
  val isSuccessorOf: IRI = apply("isSuccessorOf")
  val landingPage: IRI = apply("landingPage")
  val language: IRI = apply("language")
  val `object`: IRI = apply("object")
  val occurredAt: IRI = apply("occurredAt")
  val preview: IRI = apply("preview")
  val provider: IRI = apply("provider")
  val realizes: IRI = apply("realizes")
  val rights: IRI = apply("rights")
  val `type`: IRI = apply("type")
  val ugc: IRI = apply("ugc")
  val unstored: IRI = apply("unstored")
  val uri: IRI = apply("uri")
  val userTag: IRI = apply("userTag")
  val wasPresentAt: IRI = apply("wasPresentAt")
  val year: IRI = apply("year")
}

case class GN()
  extends Vocabulary("gn", "http://www.geonames.org/ontology#") {
  val Class: IRI = apply("Class")
  val Code: IRI = apply("Code")
  val GeonamesFeature: IRI = apply("GeonamesFeature")
  val Feature: IRI = apply("Feature")
  val Map: IRI = apply("Map")
  val RDFData: IRI = apply("RDFData")
  val WikipediaArticle: IRI = apply("WikipediaArticle")

  val alternateName: IRI = apply("alternateName")
  val colloquialName: IRI = apply("colloquialName")
  val geonamesID: IRI = apply("geonamesID")
  val countryCode: IRI = apply("countryCode")
  val historicalName: IRI = apply("historicalName")
  val name: IRI = apply("name")
  val officialName: IRI = apply("officialName")
  val population: IRI = apply("population")
  val postalCode: IRI = apply("postalCode")
  val shortName: IRI = apply("shortName")

  val childrenFeatures: IRI = apply("childrenFeatures")
  val featureClass: IRI = apply("featureClass")
  val featureCode: IRI = apply("featureCode")
  val locatedIn: IRI = apply("locatedIn")
  val locationMap: IRI = apply("locationMap")
  val nearby: IRI = apply("nearby")
  val nearbyFeatures: IRI = apply("nearbyFeatures")
  val neighbour: IRI = apply("neighbour")
  val neighbouringFeatures: IRI = apply("neighbouringFeatures")
  val parentADM1: IRI = apply("parentADM1")
  val parentADM2: IRI = apply("parentADM2")
  val parentADM3: IRI = apply("parentADM3")
  val parentADM4: IRI = apply("parentADM4")
  val parentCountry: IRI = apply("parentCountry")
  val parentFeature: IRI = apply("parentFeature")
  val wikipediaArticle: IRI = apply("wikipediaArticle")
}

case class OA()
  extends Vocabulary("oa", "http://www.w3.org/ns/oa#") {
  val Annotation: IRI = apply("Annotation")
  val Choice: IRI = apply("Choice")
  val CssSelector: IRI = apply("CssSelector")
  val CssStyle: IRI = apply("CssStyle")
  val DataPositionSelector: IRI = apply("DataPositionSelector")
  val Direction: IRI = apply("Direction")
  val FragmentSelector: IRI = apply("FragmentSelector")
  val HttpRequestState: IRI = apply("HttpRequestState")
  val Motivation: IRI = apply("Motivation")
  val PreferContainedDescriptions: IRI = apply("PreferContainedDescriptions")
  val PreferContainedIRIs: IRI = apply("PreferContainedIRIs")
  val RangeSelector: IRI = apply("RangeSelector")
  val ResourceSelection: IRI = apply("ResourceSelection")
  val Selector: IRI = apply("Selector")
  val SpecificResource: IRI = apply("SpecificResource")
  val State: IRI = apply("State")
  val Style: IRI = apply("Style")
  val SvgSelector: IRI = apply("SvgSelector")
  val TextPositionSelector: IRI = apply("TextPositionSelector")
  val TextQuoteSelector: IRI = apply("TextQuoteSelectorxxx")
  val TextualBody: IRI = apply("TextualBody")
  val TimeState: IRI = apply("TimeState")
  val XPathSelector: IRI = apply("XPathSelector")
  val annotationService: IRI = apply("annotationService")
  val assessing: IRI = apply("assessing")
  val bodyValue: IRI = apply("bodyValue")
  val bookmarking: IRI = apply("bookmarking")
  val cachedSource: IRI = apply("cachedSource")
  val canonical: IRI = apply("canonical")
  val classifying: IRI = apply("classifying")
  val commenting: IRI = apply("commenting")
  val describing: IRI = apply("describing")
  val editing: IRI = apply("editing")
  val end: IRI = apply("end")
  val exact: IRI = apply("exact")
  val hasBody: IRI = apply("hasBody")
  val hasEndSelector: IRI = apply("hasEndSelector")
  val hasPurpose: IRI = apply("hasPurpose")
  val hasScope: IRI = apply("hasScope")
  val hasSelector: IRI = apply("hasSelector")
  val hasSource: IRI = apply("hasSource")
  val hasStartSelector: IRI = apply("hasStartSelector")
  val hasState: IRI = apply("hasState")
  val hasTarget: IRI = apply("hasTarget")
  val highlighting: IRI = apply("highlighting")
  val linking: IRI = apply("linking")
  val ltrDirection: IRI = apply("ltrDirection")
  val moderating: IRI = apply("moderating")
  val motivatedBy: IRI = apply("motivatedBy")
  val prefix: IRI = apply("prefix")
  val processingLanguage: IRI = apply("processingLanguage")
  val questioning: IRI = apply("questioning")
  val refinedBy: IRI = apply("refinedBy")
  val renderedVia: IRI = apply("renderedVia")
  val replying: IRI = apply("replying")
  val rtlDirection: IRI = apply("rtlDirection")
  val sourceDate: IRI = apply("sourceDate")
  val sourceDateEnd: IRI = apply("sourceDateEnd")
  val sourceDateStart: IRI = apply("sourceDateStart")
  val start: IRI = apply("start")
  val styleClass: IRI = apply("styleClass")
  val styledBy: IRI = apply("styledBy")
  val suffix: IRI = apply("suffix")
  val tagging: IRI = apply("tagging")
  val textDirection: IRI = apply("textDirection")
  val via: IRI = apply("via")
}

case class ORE()
  extends Vocabulary("ore", "http://www.openarchives.org/ore/terms/") {
  val Aggregation: IRI = apply("Aggregation")
  val AggregatedResource: IRI = apply("AggregatedResource")
  val Proxy: IRI = apply("Proxy")
  val ResourceMap: IRI = apply("ResourceMap")
  val aggregates: IRI = apply("aggregates")
  val isAggregatedBy: IRI = apply("isAggregatedBy")
  val describes: IRI = apply("describes")
  val isDescribedBy: IRI = apply("isDescribedBy")
  val lineage: IRI = apply("lineage")
  val proxyFor: IRI = apply("proxyFor")
  val proxyIn: IRI = apply("proxyIn")
  val similarTo: IRI = apply("similarTo")
}

case class SKOS()
  extends Vocabulary("skos", "http://www.w3.org/2004/02/skos/core/") {
  val Concept: IRI = apply("Concept")
  val ConceptScheme: IRI = apply("ConceptScheme")
  val Collection: IRI = apply("Collection")
  val OrderedCollection: IRI = apply("OrderedCollection")
  val inScheme: IRI = apply("inScheme")
  val hasTopConcept: IRI = apply("hasTopConcept")
  val topConceptOf: IRI = apply("topConceptOf")
  val prefLabel: IRI = apply("prefLabel")
  val altLabel: IRI = apply("altLabel")
  val hiddenLabel: IRI = apply("hiddenLabel")
  val notation: IRI = apply("notation")
  val note: IRI = apply("note")
  val changeNote: IRI = apply("changeNote")
  val definition: IRI = apply("definition")
  val editorialNote: IRI = apply("editorialNote")
  val example: IRI = apply("example")
  val historyNote: IRI = apply("historyNote")
  val scopeNote: IRI = apply("scopeNote")
  val semanticRelation: IRI = apply("semanticRelation")
  val broader: IRI = apply("broader")
  val narrower: IRI = apply("narrower")
  val related: IRI = apply("related")
  val broaderTransitive: IRI = apply("broaderTransitive")
  val narrowerTransitive: IRI = apply("narrowerTransitive")
  val member: IRI = apply("member")
  val memberList: IRI = apply("memberList")
  val mappingRelation: IRI = apply("mappingRelation")
  val broadMatch: IRI = apply("broadMatch")
  val narrowMatch: IRI = apply("narrowMatch")
  val relatedMatch: IRI = apply("relatedMatch")
  val exactMatch: IRI = apply("exactMatch")
  val closeMatch: IRI = apply("closeMatch")
}

case class WGS84()
  extends Vocabulary("wgs84", "http://www.w3.org/2003/01/geo/wgs84_pos#") {
  val LAT: IRI = apply("lat")
  val LONG: IRI = apply("long")
  val ALT: IRI = apply("alt")

  val SpatialThing: IRI = apply("SpatialThing")
  val Point: IRI = apply("Point")
  val lat: IRI = apply("lat")
  val location: IRI = apply("location")
  val long: IRI = apply("long")
  val alt: IRI = apply("alt")
  val lat_long: IRI = apply("lat_long")
}
