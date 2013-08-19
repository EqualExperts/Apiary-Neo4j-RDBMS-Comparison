package utils.generator

import utils.NeoDB
import utils.Mode._

trait AllTopLevelPeopleWithMaxCount extends EssentialQueries {

  def runPersonWithMaxReporteesQuery(fileUrl: String, level: Int) = {
    implicit val neo4j = NeoDB(fileUrl, Embedded)
//    deleteRefNodeIfPresent
    val personName = getPersonWithMaxReportees(level)
    neo4j.shutdown
    Map(fileUrl -> personName("name"))
  }

  def runPersonWithMaxReporteesQuery(basePath: String, orgSizes: List[String], levels: Range) {
    //     basePath = "D:/rnd/apiary"
//    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable"
//        val orgSizes = List("1k", "10k", "100k", "1m")
//    val orgSizes = List("1k")
//    val fileUrls = orgSizes map { basePath + "/NEO4J_DATA/apiary_" + _ + "_l" }
    val fileUrls = orgSizes map { basePath + _ + "_l" }
    val results = fileUrls flatMap { fileUrl =>
      (levels flatMap { level =>
        val completeUrl = fileUrl + level
        info("Finding Person With Max Reportee for ==> %s\n", completeUrl)
        runPersonWithMaxReporteesQuery(completeUrl, level)
      }) toList
    }
//    info("RESULTS:\n%s", results mkString "\n")
    results
  }
}
