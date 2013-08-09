package utils.generator

import utils.Mode._
import utils.NeoDB

object SubNameQueryRunner extends App
with CypherQueryExecutor with MemoryStatsReporter {

  override def main(args: Array[String]) = {
    memStats

    val totalLevels = 3
    // Get this manually from neo-shell
    val params = Map("name" -> "first88 last136")

    //	  val basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable"
    val fileUrl = basePath + "/NEO4J_DATA/apiary_100k_l" + totalLevels
    implicit val neo4j = NeoDB(fileUrl, Embedded)
    val visibilityLevel = totalLevels - 1
    val subNameCql =
      """
        |start n = node:Person(name = {name})
        |match p = n-[:DIRECTLY_MANAGES*1..%d]->m
        |return nodes(p);
      """.format(visibilityLevel).stripMargin
      val (_, coldCacheExecTime) = execute(subNameCql, params)
      val (_, warmCacheExecTime) = execute(subNameCql, params)
      printf("For Level %d => Cold Cache Exec Time = %d (ms)\n", totalLevels, coldCacheExecTime)
      printf("For Level %d => Warm Cache Exec Time = %d (ms)\n", totalLevels, warmCacheExecTime)
      neo4j.shutdown
  }
}
