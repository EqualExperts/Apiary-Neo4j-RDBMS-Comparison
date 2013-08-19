package utils.generator

import utils.NeoDB

object OverallAggQueryRunner extends CypherQueryExecutor with EssentialQueries with MemoryStatsReporter {
  def runFor(level: Int)(implicit neo4j: NeoDB) = {
//    deleteRefNodeIfPresent(neo4j)

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
}
