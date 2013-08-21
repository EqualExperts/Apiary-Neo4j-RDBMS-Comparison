package utils.generator

import utils.NeoDB
import utils.Mode._

object SubNameQueryRunner extends App
with CypherQueryExecutor with MemoryStatsReporter {

  def runFor(level: Int, params: Map[String, Any])(implicit neo4j: NeoDB) = {
    val visibilityLevel = level - 1
    val subNameCql =
      """
        |start n = node:Person(name = {name})
        |match p = n-[:DIRECTLY_MANAGES*1..%d]->m
        |return nodes(p);
      """.format(visibilityLevel).stripMargin
    val (_, coldCacheExecTime) = execute(subNameCql, params)
    val (_, warmCacheExecTime) = execute(subNameCql, params)
    val (_, hotCacheExecTime) = execute(subNameCql, params)
    val queryName = getClass.getSimpleName.replace("$", "").replace("Runner", "")
    val resultString = "%s => For Level %d => %s Cache Exec Time = %d (ms)\n"
    val coldCacheResult = resultString.format(queryName, level, "Cold", coldCacheExecTime)
    val warmCacheResult = resultString.format(queryName, level, "Warm", warmCacheExecTime)
    val hotCacheResult = resultString.format(queryName, level, "Hot", hotCacheExecTime)
    List(coldCacheResult, warmCacheResult, hotCacheResult)
  }

  override def main(args: Array[String]) {
    memStats
    //     basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable/NEO4J_DATA/apiary_"
    val orgSize = "1m"
    val level = 8
    val orgSizeAndLevel = orgSize + "_l"  + level
    val completeUrl = basePath + orgSizeAndLevel
    val topLevelPerson = TopLevel().names(orgSizeAndLevel)
    val params = Map[String, Any]("name" -> topLevelPerson)
    info("Top Level Person is ==> %s", topLevelPerson)
    implicit val neo4j = NeoDB(completeUrl, Embedded)
    val result = runFor(level, params)
    neo4j.shutdown
    info("RESULTS:\n%s", result mkString "\n")
    memStats
  }
}
