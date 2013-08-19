package utils.generator

import utils.NeoDB

object SubAggQueryRunner extends App
with CypherQueryExecutor with EssentialQueries with MemoryStatsReporter {
  def runFor(neo4j: NeoDB, level: Int) = {
//    deleteRefNodeIfPresent(neo4j)

    val params = getPersonWithMaxReportees(neo4j, level)
    val visibilityLevel = level - 1 //always max visibility
    val traversableLevels = level - 1 //actually its totalLevels - currentlevel
    val subAggCql =
      """
        |start n = node:Person(name = {name})
        |match n-[:DIRECTLY_MANAGES*0..%d]->m-[:DIRECTLY_MANAGES*1..%d]->o
        |where n.level + %d >= m.level
        |return m.name as Subordinate, count(o) as Total;
      """.format(traversableLevels, traversableLevels, visibilityLevel).stripMargin

    val (_, coldCacheExecTime) = execute(neo4j, subAggCql, params)
    val (_, warmCacheExecTime) = execute(neo4j, subAggCql, params)
    val queryName = getClass.getSimpleName.replace("$", "").replace("Runner", "")
    val resultString = "%s => For Level %d => %s Cache Exec Time = %d (ms)\n"
    val coldCacheResult = resultString.format(queryName, level, "Cold", coldCacheExecTime)
    val warmCacheResult = resultString.format(queryName, level, "Warm", warmCacheExecTime)
    List(coldCacheResult, warmCacheResult)
  }
}

