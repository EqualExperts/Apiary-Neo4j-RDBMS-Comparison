package utils.generator

import utils.Mode._
import utils.NeoDB

object SubordinateNameQuery extends App with CypherQueryExecutor {

  override def main(args: Array[String]) = {
    val totalLevels = 3
    // Get this manually from neo-shell
    val params = Map("name" -> "first88 last136")

    //	  val basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable"
    val fileUrl = basePath + "/NEO4J_DATA/apiary_100k_l" + totalLevels
    val subordinateNameCql =
      """
        |start n = node:Person(name = {name})
        |match p = n-[:DIRECTLY_MANAGES*1..%d]->m
        |return nodes(p);
      """
      val neo4j = NeoDB(fileUrl, Embedded)
      val visibilityLevel = totalLevels - 1
      subordinateNameCql.format(visibilityLevel).stripMargin
      val (_, coldCacheExecTime) = execute(neo4j, subordinateNameCql, params)
      val (_, warmCacheExecTime) = execute(neo4j, subordinateNameCql, params)
      println("For Level %d => Cold Cache Exec Time = %d (ms)\n", totalLevels, coldCacheExecTime)
      println("For Level %d => Warm Cache Exec Time = %d (ms)\n", totalLevels, warmCacheExecTime)
      neo4j.shutdown
  }
}
