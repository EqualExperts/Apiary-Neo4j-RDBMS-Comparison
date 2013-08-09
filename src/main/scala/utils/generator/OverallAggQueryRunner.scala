package utils.generator

import utils.NeoDB
import utils.Mode._

object OverallAggQueryRunner extends App with CypherQueryExecutor {

  override def main(args: Array[String]) = {
    val totalLevels = 3

    //	  val basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable"
    val fileUrl = basePath + "/NEO4J_DATA/apiary_100k_l" + totalLevels

    implicit val neo4j = NeoDB(fileUrl, Embedded)
    val aggCql =
      """
        |start n = node(*)
        |return n.level as Level, count(n) as Total
        |order by Level;
      """.stripMargin

    val (_, coldCacheExecTime) = execute(aggCql)
    val (_, warmCacheExecTime) = execute(aggCql)
    printf("For Level %d => Cold Cache Exec Time = %d (ms)\n", totalLevels, coldCacheExecTime)
    printf("For Level %d => Warm Cache Exec Time = %d (ms)\n", totalLevels, warmCacheExecTime)
    neo4j.shutdown
  }
}
