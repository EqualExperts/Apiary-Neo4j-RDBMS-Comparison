package utils.generator

import org.neo4j.graphdb.{RelationshipType, Node, GraphDatabaseService}
import DistributionStrategy._
import scala.annotation.tailrec

private class Neo4JBuilder (val neo4j: GraphDatabaseService, val peopleAtLevels: Map[Int, List[String]], val managingMax: Int) {

  type Relationship = (Node, Node)
  val maxLevels = peopleAtLevels.size
  private val PERSON = "Person"
  private val PERSON_NAME = "name"
//  private val personIndex = neo4j.index.forNodes(PERSON)

  private object DIRECTLY_MANAGES extends RelationshipType {
    def name = getClass.getSimpleName.replace("$", "")
  }

  private object INDIRECTLY_MANAGES extends RelationshipType {
    def name = getClass.getSimpleName.replace("$", "")
  }

  private def toNode(neo4j: GraphDatabaseService, level: Int, name: String): Node = {
    val personNode = neo4j.createNode()
    personNode.setProperty(PERSON_NAME, name)
    personNode.setProperty("level", level)
    personNode.setProperty("type", PERSON)
    personNode
  }

//  private def index(node: Node) = personIndex.add(node, PERSON_NAME, node.getProperty(PERSON_NAME))

//  private def indexAll(nodesAtLevels: Map[Int, List[Node]]) = nodesAtLevels.values.flatten.foreach(index)

  private def persistNodes(graphDb: GraphDatabaseService) =
    peopleAtLevels map {
      case (level, people) => (level, people map { toNode (graphDb, level, _) })
    } withDefaultValue Nil

  private def persistRelationships(relationships: List[Relationship]) =
    relationships map { case (manager, reportee) => manager.createRelationshipTo(reportee, DIRECTLY_MANAGES) }

  private def makeRelationshipsBetween(nodesAtLevels: Map[Int, List[Node]], distributionStrategy: DistributionStrategy): List[Relationship] = {

    def parentToChildrenRelationships(parent: Node, children: List[Node]): List[Relationship] =
      for (child <- children) yield (parent, child)

    def between(parentNodes: List[Node], childNodes: List[Node]) : List[Relationship] = {
      val manages = distributionStrategy match {
        case Contiguous => managingMax
        case Even =>   Math.min(Math.ceil(childNodes.length.toFloat/parentNodes.length).toInt, managingMax)
      }

      @tailrec
      def between0(acc: List[Relationship], parentNodes: List[Node], from: Int, to: Int): List[Relationship] =
        parentNodes match {
          case Nil =>  acc
          case parent :: rest => {
            val rs = parentToChildrenRelationships(parent, childNodes.slice(from, to))
            between0(rs ::: acc, rest, from + manages, to + manages)
          }
        }
      between0(Nil, parentNodes, 0, manages)
    }

    @tailrec
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

  def build(distributionStrategy: DistributionStrategy) = {
    val people = measure("Persisting People", persistNodes, neo4j)
    //measure("Indexing People", indexAll, people)
    info("Creating Relationships using %s Distribution strategy", distributionStrategy)
    val managerReporteePairs = makeRelationshipsBetween(people, distributionStrategy)
    measure("Persisting Relationships", persistRelationships, managerReporteePairs)
  }
}
