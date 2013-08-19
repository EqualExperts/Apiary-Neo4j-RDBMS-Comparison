package utils.generator

import utils.NeoDB
import org.neo4j.cypher.EntityNotFoundException

trait EssentialQueries extends CypherQueryExecutor {
  def deleteRefNodeIfPresent(neo4j: NeoDB) = {
    val deleteRefNodeCql =
      """
        |start n = node(0)
        |delete n;
      """.stripMargin

    try {
      execute(neo4j, deleteRefNodeCql)
      info("Deleted Reference Node")
    } catch {
      case e: EntityNotFoundException => info("No Reference Node to Delete")
    }
  }

  def getPersonWithMaxReportees(neo4j: NeoDB, level: Int) : Map[String, Any] = {
    val topLevelCql =
      """
        |start n = node(*)
        |where n.level = 1
        |return n
      """.stripMargin
    val (result, _) = execute(neo4j, topLevelCql)

    val totalSubCql =
      """
        |start n = node:Person(name = {name})
        |match n-[:DIRECTLY_MANAGES*1..%d]->m
        |return count(m) as count;
      """.stripMargin.format(level)

    val namesWithCount = result.map { node =>
      val name = node("name").toString
      val params = Map("name" -> name)
      val (countResult, _) = execute(neo4j, totalSubCql, params)
      val count = countResult.map { rs => rs("count") }
        .find(_ => true)
        .get.asInstanceOf[Long]
      name -> count
    }.toMap

    val maxCount = namesWithCount.values.max
    val name = namesWithCount.find { case (n, c) => c == maxCount }.get
    val personWithMaxReporteeCount = Map[String, Any]("name" -> name)
    info("Person With Max Reportee Count = " + personWithMaxReporteeCount)
    personWithMaxReporteeCount
  }
}
