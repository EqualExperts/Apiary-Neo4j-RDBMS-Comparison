package utils.generator

import utils.Mode._
import utils.NeoDB

object SubAggQueryRunner extends App
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
    val visibilityLevel = totalLevels - 1 //always max visibility
    val traversableLevels = totalLevels - 1 //actually its totalLevels - currentlevel
    val subAggCql =
      """
        |start n = node:Person(name = {name})
        |match n-[:DIRECTLY_MANAGES*0..%d]->m-[:DIRECTLY_MANAGES*1..%d]->o
        |where n.level + %d >= m.level
        |return m.name as Subordinate, count(o) as Total;
      """.format(traversableLevels, traversableLevels, visibilityLevel).stripMargin

    val (_, coldCacheExecTime) = execute(subAggCql, params)
    val (_, warmCacheExecTime) = execute(subAggCql, params)
    printf("For Level %d => Cold Cache Exec Time = %d (ms)\n", totalLevels, coldCacheExecTime)
    printf("For Level %d => Warm Cache Exec Time = %d (ms)\n", totalLevels, warmCacheExecTime)
    neo4j.shutdown
  }
}

