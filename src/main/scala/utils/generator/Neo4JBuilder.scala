package utils.generator

import org.neo4j.graphdb.{RelationshipType, Node, GraphDatabaseService}

private class Neo4JBuilder (val neo4j: GraphDatabaseService, val peopleAtLevels: Map[Int, List[String]], val managingMax: Int) {
  type Relationship = (Node, Node)

  val maxLevels = peopleAtLevels.size

  private object DIRECTLY_MANAGES extends RelationshipType {
    def name = "DIRECTLY_MANAGES"
  }

  private object INDIRECTLY_MANAGES extends RelationshipType {
    def name = "INDIRECTLY_MANAGES"
  }

  private def toNode(graphDb: GraphDatabaseService, level: Int, person: String): Node = {
    val personNode = graphDb.createNode()
    personNode.setProperty("name", person)
    personNode.setProperty("level", level)
    personNode.setProperty("type", "Person")
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
//    logToConsole("INFO", "Creating People...")
    val people = persistNodes(neo4j)
//    logToConsole("INFO", "Done Creating People")
    val managerReporteePairs = makeRelationshipsBetween(people)
//    logToConsole("INFO", "Creating Relationships...")
    persistRelationships(managerReporteePairs)
//    logToConsole("INFO", "Done Creating Relationships")
  }
}
