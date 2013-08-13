package utils.generator

import utils.NeoDB

trait PersonWithMaxReportees extends CypherQueryExecutor {
  def getPersonWithMaxReportees(level: Int)(implicit neo4j: NeoDB) : Map[String, Any] = {
    val topLevelCql =
      """
        |start n = node(*) where n.level = 1 return n.name as name;
      """.stripMargin
    val (result, _) = execute(topLevelCql)

    val totalSubCql =
      """
        |start n = node:Person(name = {name})
        |match n-[:DIRECTLY_MANAGES*1..%d]->m
        |return count(m) as count;
      """.stripMargin.format(level)

    val namesWithCount = result.map { node =>
      val name = node("name").toString
      val params = Map("name" -> name)
      val (countResult, _) = execute(totalSubCql, params)
      val count = countResult.map { rs => rs("count") }
        .find(_ => true)
        .get
        .asInstanceOf[Long]
      name -> count
    }.toMap

    val maxCount = namesWithCount.values.max
    val name = namesWithCount.find { case (n, c) => c == maxCount }.get
    val personWithMaxReporteeCount = Map[String, Any]("name" -> name)
    println("Person With Max Reportee Count = " + personWithMaxReporteeCount)
    personWithMaxReporteeCount
  }
}
