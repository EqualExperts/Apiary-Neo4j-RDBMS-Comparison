package utils.generator

import utils.NeoDB
import utils.Mode._

object SubAggQueryRunner extends App
with CypherQueryExecutor with MemoryStatsReporter {

  def runFor(level: Int, params: Map[String, Any])(implicit neo4j: NeoDB) = {
    val visibilityLevel = level - 1 //always max visibility
    val traversableLevels = level - 1 //actually its totalLevels - currentlevel
    val subAggCql =
      """
        |start n = node:Person(name = {name})
        |match n-[:DIRECTLY_MANAGES*0..%d]->m-[:DIRECTLY_MANAGES*1..%d]->o
        |where n.level + %d >= m.level
        |return m.name as Subordinate, count(o) as Total;
      """.format(traversableLevels, traversableLevels, visibilityLevel).stripMargin

    val (_, coldCacheExecTime) = execute(subAggCql, params)
    val (_, warmCacheExecTime) = execute(subAggCql, params)
    val queryName = getClass.getSimpleName.replace("$", "").replace("Runner", "")
    val resultString = "%s => For Level %d => %s Cache Exec Time = %d (ms)\n"
    val coldCacheResult = resultString.format(queryName, level, "Cold", coldCacheExecTime)
    val warmCacheResult = resultString.format(queryName, level, "Warm", warmCacheExecTime)
    List(coldCacheResult, warmCacheResult)
  }

  override def main(args: Array[String]) {
    memStats
    //     basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable/NEO4J_DATA/apiary_"
    val orgSize = "1k"
    val level = 3
    val orgSizeAndLevel = orgSize + "_l"  + level
    val completeUrl = basePath + orgSizeAndLevel
//    val topLevelPerson = TopLevel().newNames(basePath, List(orgSize), level to level)
    val topLevelPerson = TopLevel().names(orgSizeAndLevel)
    val params = Map[String, Any]("name" -> topLevelPerson)
    info("Top Level Person is ==> %s", topLevelPerson)
    implicit val neo4j = NeoDB(completeUrl, Embedded)
    val result = runFor(level, params)
    neo4j.shutdown
    result
    info("RESULTS:\n%s", result mkString "\n")
    memStats
  }

}

