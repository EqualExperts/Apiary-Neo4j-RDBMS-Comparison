package utils

import org.neo4j.graphdb.{Direction, GraphDatabaseService, Node, RelationshipType}
import scala.util.Random

/**
 * i/ps =>
 * 1) total people - total
 * 2) indirectRelationShips -idr  n (max)
 * 3) directRelationships:  -dr n (max)
 * 4) levelsOfDirectRelationships: -levels (valid values: 2 to 10)
 */

object DIRECTLY_MANAGES extends RelationshipType {
  def name = "DIRECTLY_MANAGES"
}

object INDIRECTLY_MANAGES extends RelationshipType {
  def name = "INDIRECTLY_MANAGES"
}

class OrganizationBuilder private (val names: List[String], val managingMax: Int = 1, val directlyReportingToMax: Int = 1, val indirectlyReportingToMax: Int = 0) {
  require(managingMax >= 0, "For an Organisation, a person manages, at max 0..* person")
  require(directlyReportingToMax >= 0, "For an Organisation, a person can report to max  0..* person")
  type Relationship = (Node, Node)

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
    println("Cannot distribute people properly in the hierarchy, try increasing the value of personManagingMax above " + managingMax)
    println("Alternatively, Lessen the people at ")
    levels match {
      case level :: Nil => println("=> level " + level)
      case _ => println("=> levels " + levels)
    }
    sys.error("Could Not Build Organization!")
  }

  def maxLevels = peopleAtLevels.size

  def totalPeople = peopleAtLevels.values.foldLeft(0)(_ + _.length)

  def buildUsing(neo4j: NeoDB): Unit = {

    def toNode(graphDb: GraphDatabaseService, level: Int, person: String): Node = {
      val personNode = graphDb.createNode()
      personNode.setProperty("name", person)
      personNode.setProperty("level", level)
      personNode.setProperty("type", "Person")
//      println("Created Person " + person)
      personNode
    }

    def persistNodes(graphDb: GraphDatabaseService) =
      peopleAtLevels.toMap map {
        case (level, people) => (level, people map { toNode (graphDb, level, _) })
      } withDefaultValue Nil

    def persistRelationships(relationships: List[Relationship]) =
      relationships map { case (manager, reportee) => manager.createRelationshipTo(reportee, DIRECTLY_MANAGES) }

    def makeRelationshipsBetween(nodesAtLevels: Map[Int, List[Node]]): List[Relationship] = {

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

    printf("Total in Org = %d people\n", totalPeople)
    illFormedLevels match {
      case (Nil, _) => {
        neo4j usingTx { graphDb =>
          val people = persistNodes(graphDb)
          val managerReporteePairs = makeRelationshipsBetween(people)
          persistRelationships(managerReporteePairs)
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

object Test extends App {
  override def main(args: Array[String]) = {
    val parentPath = "src" :: "main" :: "resources" :: Nil
    val firstNames = NamesLoader(parentPath, List("firstNames.txt"))
    val lastNames  = NamesLoader(parentPath, List("lastNames.txt"))

    val names = for {
      firstName <- firstNames
      lastName <- lastNames
    } yield firstName + " " + lastName

    val neoDb = NeoDB("http://localhost:7474/db/data")


    /**
     * case 1:
     * total people in organisation = 1000, with Levels = 3, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *  At Level 1 => 40  (manages 4)
     *  At Level 2 => 160 (manages 5)
     *  At Level 3 => 800
     *  Total => 1000
     */

//    val builder = OrganizationBuilder(Random.shuffle(names), withPersonManagingMaxOf = 5)
//                        .withPeopleAtLevel(1, 40)
//                        .withPeopleAtLevel(2, 160)
//                        .withPeopleAtLevel(3, 800)


    /**
     * case 2:
     * total people in organisation = 1000 with levels = 4, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     * At Level 1 => 10    (manages 5)
     * At Level 2 => 50    (manages 4)
     * At Level 3 => 200   (manages 4)
     * At Level 4 => 740
     * Total => 1000
     */

//    val builder  = OrganizationBuilder(Random.shuffle(names), withPersonManagingMaxOf = 5)
//                      .withPeopleAtLevel(1, 10)
//                      .withPeopleAtLevel(2, 50)
//                      .withPeopleAtLevel(3, 200)
//                      .withPeopleAtLevel(4, 740)


    /**
     * case 3:
     * total people in organisation = 1000 with levels = 5, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *
     * At Level 1 => 3     (manages 5)
     * At Level 2 => 15    (manages 5)
     * At Level 3 => 75    (manages 4)
     * At Level 4 => 300   (manages 3)
     * At Level 5 => 607
     * Total => 1000
     */

    val builder  = OrganizationBuilder(Random.shuffle(names), withPersonManagingMaxOf = 5)
                      .withPeopleAtLevel(1, 3)
                      .withPeopleAtLevel(2, 15)
                      .withPeopleAtLevel(3, 75)
                      .withPeopleAtLevel(4, 300)
                      .withPeopleAtLevel(5, 607)

    builder buildUsing neoDb
  }
}

