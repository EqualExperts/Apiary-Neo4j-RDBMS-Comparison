package utils.generator

import utils.NeoDB
import utils.Mode._

object OverallAggQueryRunner extends App with CypherQueryExecutor with MemoryStatsReporter {
  def runFor(level: Int)(implicit neo4j: NeoDB) = {
    val aggCql =
      """
        |start n = node(*)
        |return n.level as Level, count(n) as Total
        |order by Level;
      """.stripMargin

    val (_, coldCacheExecTime) = execute(aggCql)
    val (_, warmCacheExecTime) = execute(aggCql)
    val queryName = getClass.getSimpleName.replace("$", "").replace("Runner", "")
    val resultString = "%s => For Level %d => %s Cache Exec Time = %d (ms)\n"
    val coldCacheResult = resultString.format(queryName, level, "Cold", coldCacheExecTime)
    val warmCacheResult = resultString.format(queryName, level, "Warm", warmCacheExecTime)
    List(coldCacheResult, warmCacheResult)
  }

  def runOverallAggQuery(fileUrl: String, level: Int) : List[String] = {
    implicit val neo4j = NeoDB(fileUrl, Embedded)
    val result = OverallAggQueryRunner.runFor(level)
    neo4j.shutdown
    result
  }

  override def main(args: Array[String]) {
    memStats
    //     basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable"
    val orgSize = "1k"
    val level = 3

    val completeUrl = basePath + "/NEO4J_DATA/apiary_" + orgSize + "_l"  + level
    val result = runOverallAggQuery(completeUrl, level)

    info("RESULTS:\n%s", result mkString "\n")
  }
}