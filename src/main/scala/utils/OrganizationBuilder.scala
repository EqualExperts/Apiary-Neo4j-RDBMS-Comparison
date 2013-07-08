package utils

import org.neo4j.graphdb.{Direction, GraphDatabaseService, Node, RelationshipType}
import scala.util.Random
import java.util.Date

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

  def maxLevels = peopleAtLevels.size

  def totalPeople = peopleAtLevels.values.foldLeft(0)(_ + _.length)

  private def logToConsole(logLevel: String, message: String) = {
    printf("[%s] [%s] %s\n", logLevel, new Date(),  message)
  }

  private class Neo4JBuilder (val neo4j: GraphDatabaseService, val peopleAtLevels: Map[Int, List[String]]) {
    type Relationship = (Node, Node)

    object DIRECTLY_MANAGES extends RelationshipType {
      def name = "DIRECTLY_MANAGES"
    }

    object INDIRECTLY_MANAGES extends RelationshipType {
      def name = "INDIRECTLY_MANAGES"
    }

    private def toNode(graphDb: GraphDatabaseService, level: Int, person: String): Node = {
      val personNode = graphDb.createNode()
      personNode.setProperty("name", person)
      personNode.setProperty("level", level)
      personNode.setProperty("type", "Person")
      //      println("Created Person " + person)
      personNode
    }

    private def persistNodes(graphDb: GraphDatabaseService) =
      peopleAtLevels map {
        case (level, people) => (level, people map { toNode (graphDb, level, _) })
      } withDefaultValue Nil

    private def persistRelationships(relationships: List[Relationship]) =
      relationships map { case (manager, reportee) => manager.createRelationshipTo(reportee, DIRECTLY_MANAGES) }

    private def makeRelationshipsBetween(nodesAtLevels: Map[Int, List[Node]]): List[Relationship] = {

      def parentToChildrenRelationships(parent: Node, children: List[Node]): List[Relationship] =
        for (child <- children) yield (parent, child)

      def between(parentNodes: List[Node], childNodes: List[Node]) : List[Relationship] = {
        def between0(acc: List[Relationship], parentNodes: List[Node], from: Int, to: Int): List[Relationship] =
          parentNodes match {
            case Nil =>  acc
            case parent :: rest => {
              val rs = parentToChildrenRelationships(parent, childNodes.slice(from, to))
              between0(rs ::: acc, rest, from + managingMax, to + managingMax)
            }
          }
        between0(Nil, parentNodes, 0, managingMax)
      }

      def relationships(acc: List[Relationship], level: Int) : List[Relationship] = {
        if (level == maxLevels) acc
        else {
          val parentNodes = nodesAtLevels(level)
          val childNodes  = nodesAtLevels(level + 1)
          relationships(between(parentNodes, childNodes) ::: acc, level + 1)
        }
      }
      relationships(Nil, level = 1)
    }

    def build = {
      logToConsole("INFO", "Creating People...")
      val people = persistNodes(neo4j)
      logToConsole("INFO", "Done Creating People")
      val managerReporteePairs = makeRelationshipsBetween(people)
      logToConsole("INFO", "Creating Relationships...")
      persistRelationships(managerReporteePairs)
      logToConsole("INFO", "Done Creating Relationships")
    }
  }

  def buildWith(neo4j: NeoDB): Unit = {
    printf("Total in Org = %d people\n", totalPeople)
    illFormedLevels match {
      case (Nil, _) => {
        neo4j usingTx { graphDb =>
          new Neo4JBuilder(graphDb, peopleAtLevels.toMap).build
        }
      }
      case (levels, _) => showLevelErrorMessage(levels.toList)
    }
  }
}

object OrganizationBuilder {
  def apply(names: List[String], withPersonManagingMaxOf: Int = 1, withPersonDirectlyReportingToMaxOf : Int = 1) =
    new OrganizationBuilder(names, withPersonManagingMaxOf, withPersonDirectlyReportingToMaxOf)
}