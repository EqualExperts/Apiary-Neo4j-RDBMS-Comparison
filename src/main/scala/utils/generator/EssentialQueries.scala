package utils.generator

import utils.NeoDB
import org.neo4j.cypher.EntityNotFoundException

trait EssentialQueries extends CypherQueryExecutor {
  def deleteRefNodeIfPresent(implicit neo4j: NeoDB) = {
    val deleteRefNodeCql =
      """
        |start n = node(0)
        |delete n;
      """.stripMargin

    try {
      execute(deleteRefNodeCql)
      info("Deleted Reference Node")
    } catch {
      case e: EntityNotFoundException => info("No Reference Node to Delete")
    }
  }

  def getPersonWithMaxReportees(level: Int)(implicit neo4j: NeoDB) : Map[String, Any] = {

    val personWithMaxReporteesCql =
      """
        |start n = node(*)
        |match n-[:DIRECTLY_MANAGES*1..%d]->m
        |where n.level = 1
        |return n.name as name, count(m) as count
        |order by count desc
        |limit 1;
      """.stripMargin.format(level)

    val (result, _) = execute(personWithMaxReporteesCql)
    val params = result.map { node => "name" -> node("name") }.toMap
    info("Person with Max Reportees..." + params.toString() )
    params
  }
}
