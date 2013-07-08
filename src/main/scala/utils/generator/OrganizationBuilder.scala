package utils.generator

import java.util.Date
import utils.NeoDB
import java.sql.{Connection}

/**
 * i/ps =>
 * 1) total people - total
 * 2) indirectRelationShips -idr  n (max)
 * 3) directRelationships:  -dr n (max)
 * 4) levelsOfDirectRelationships: -levels (valid values: 2 to 10)
 */

class OrganizationBuilder private (val names: List[String], val managingMax: Int = 1, val directlyReportingToMax: Int = 1, val indirectlyReportingToMax: Int = 0) {
  require(managingMax >= 0, "For an Organisation, a person manages, at max 0..* person")
  require(directlyReportingToMax >= 0, "For an Organisation, a person can report to max  0..* person")

  var from = 0
  val peopleAtLevels = scala.collection.mutable.Map[Int, List[String]]() withDefaultValue Nil

  private def isManageable(upperLevel: Int, lowerLevel: Int) = {
    val totalUpper = peopleAtLevels(upperLevel).length.toDouble
    val totalLower = peopleAtLevels(lowerLevel).length.toDouble
    (totalLower /  managingMax.toDouble) <= totalUpper
  }

  private def illFormedLevels =
    peopleAtLevels.toMap filter { case (level, _) =>
      !isManageable(level, level + 1)
    } unzip

  def withPeopleAtLevel(level: Int, howMany: Int): OrganizationBuilder = {
    require(level > 0, "Level needs to be greater than 0")
    val people = names.slice(from, from + howMany)
    from = howMany
    peopleAtLevels += (level -> people)
    printf("At Level %d (%d people) = %s\n", level, people.size, people)
    this
  }

  private def showLevelErrorMessage(levels: List[Int]) = {
    logToConsole("ERROR", "Cannot distribute people properly in the hierarchy! Options: ")
    logToConsole("ERROR", "1) Try increasing the value of personManagingMax above " + managingMax)
    logToConsole("ERROR" ,"2) Alternatively, Lessen the people at ")
    levels match {
      case level :: Nil => logToConsole("ERROR", "=> level " + level)
      case _ => logToConsole("ERROR", "=> levels " + levels)
    }
    sys.error("Could Not Build Organization!")
  }

  def totalPeople = peopleAtLevels.values.foldLeft(0)(_ + _.length)

  private def logToConsole(logLevel: String, message: String) = {
    printf("[%s] [%s] %s\n", logLevel, new Date(),  message)
  }

  def buildWith(neo4j: NeoDB): Unit = {
    printf("Total in Org = %d people\n", totalPeople)
    illFormedLevels match {
      case (Nil, _) => neo4j usingTx { graphDb =>
          new Neo4JBuilder(graphDb, peopleAtLevels.toMap, managingMax).build
        }
      case (levels, _) => showLevelErrorMessage(levels.toList)
    }
  }

  def buildWith(mysql: Connection): Unit = {
    printf("Total in Org = %d people\n", totalPeople)
    illFormedLevels match {
      case (Nil, _)    => new MySQLBuilder(mysql, peopleAtLevels.toMap).build
      case (levels, _) => showLevelErrorMessage(levels.toList)
    }
  }
}

object OrganizationBuilder {
  def apply(names: List[String], withPersonManagingMaxOf: Int = 1, withPersonDirectlyReportingToMaxOf : Int = 1) =
    new OrganizationBuilder(names, withPersonManagingMaxOf, withPersonDirectlyReportingToMaxOf)
}