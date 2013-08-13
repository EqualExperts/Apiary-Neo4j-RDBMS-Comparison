package utils.generator

import utils.Mode._
import utils.NeoDB

object AllMeasurementQueriesRunner extends App with MemoryStatsReporter {

  def runSubNameQuery(fileUrl: String, level: Int) = {
    implicit val neo4j = NeoDB(fileUrl + level, Embedded)
    val result = SubNameQueryRunner.runFor(level)
    neo4j.shutdown
    result
  }
  def runSubAggQuery(fileUrl: String, level: Int) = {
    implicit val neo4j = NeoDB(fileUrl + level, Embedded)
    val result = SubAggQueryRunner.runFor(level)
    neo4j.shutdown
    result
  }
  def runOverallAggQuery(fileUrl: String, level: Int) = {
    implicit val neo4j = NeoDB(fileUrl + level, Embedded)
    val result = OverallAggQueryRunner.runFor(level)
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
    val fileUrl = basePath + "/NEO4J_DATA/apiary_100k_l"
    val results = ((3 to 8) flatMap { runQuery(fileUrl, _) }).toList
    println("RESULTS:" + results.mkString("\n"))
  }
}
