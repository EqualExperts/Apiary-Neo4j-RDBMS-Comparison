package utils.generator

import utils.Mode._
import utils.NeoDB

object AllMeasurementQueriesRunner extends App with MemoryStatsReporter {

  def runSubNameQuery(fileUrl: String, level: Int) = {
    val neo4j = NeoDB(fileUrl + level, Embedded)
    val result = SubNameQueryRunner.runFor(neo4j, level)
    neo4j.shutdown
    result
  }

  def runSubAggQuery(fileUrl: String, level: Int) = {
    val neo4j = NeoDB(fileUrl + level, Embedded)
    val result = SubAggQueryRunner.runFor(neo4j, level)
    neo4j.shutdown
    result
  }
  def runOverallAggQuery(fileUrl: String, level: Int) = {
    implicit val neo4j = NeoDB(fileUrl + level, Embedded)
    val result = OverallAggQueryRunner.runFor(neo4j, level)
    neo4j.shutdown
    result
  }

  def runQuery(fileUrl: String, level: Int) = {
    val subNameResults = runSubNameQuery(fileUrl, level)
    val subAggResults = runSubAggQuery(fileUrl, level)
    val overallAggResults = runOverallAggQuery(fileUrl, level)
    subNameResults ::: subAggResults ::: overallAggResults
  }

  override def main(args: Array[String]) {
    memStats
//     basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable"
//    val orgSizes = List("1k", "10k", "100k", "1m")
    val orgSizes = List("1k")
    val fileUrls = orgSizes map { basePath + "/NEO4J_DATA/apiary_" + _ + "_l" }
    val results = fileUrls map { fileUrl =>
      ((3 to 8) flatMap { level =>
        val completeUrl = fileUrl + level
        info("Taking measurements for ---> " + completeUrl)
        runQuery(completeUrl, level) }).toList
    }
    println("RESULTS:" + results.mkString("\n"))

    memStats
  }
}
