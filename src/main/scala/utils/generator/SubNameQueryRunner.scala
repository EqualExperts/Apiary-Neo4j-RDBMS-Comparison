package utils.generator

import utils.NeoDB

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
    val queryName = getClass.getSimpleName.replace("$", "").replace("Runner", "")
    val resultString = "%s => For Level %d => %s Cache Exec Time = %d (ms)\n"
    val coldCacheResult = resultString.format(queryName, level, "Cold", coldCacheExecTime)
    val warmCacheResult = resultString.format(queryName, level, "Warm", warmCacheExecTime)
    List(coldCacheResult, warmCacheResult)
  }
}
