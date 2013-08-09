package utils.generator

import scala.Predef._
import utils.NeoDB

trait CypherQueryExecutor {
  def execute(cql: String, params: Map[String, Any] = Map[String, Any]())(implicit neo4j: NeoDB) = {
    info("Executing Parameteric Cypher query %s\n", cql)
    val startTime = System.currentTimeMillis
    val result = neo4j.execute(cql, params)
    val difference = System.currentTimeMillis - startTime
    info("Execution result %s\n", result.dumpToString)
    info("Execution Took %d (ms)\n", difference)
    (result, difference)
  }
}
