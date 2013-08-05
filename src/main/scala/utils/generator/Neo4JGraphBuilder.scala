package utils.generator

import org.neo4j.graphdb.{Relationship, RelationshipType, Node, GraphDatabaseService}
import DistributionStrategy._
import scala.annotation.tailrec

private class Neo4JGraphBuilder (val neo4j: GraphDatabaseService, override val peopleAtLevels: Map[Int, List[String]], override val managingMax: Int) extends Neo4JBuilder[Node, Relationship](peopleAtLevels, managingMax) {

  private val personIndex = createIndex(PERSON)

  protected override def createIndex(name: String) = neo4j.index.forNodes(name)

  protected override def persistNode(level: Int, name: String): Node = {
    val personNode = neo4j.createNode()
    personNode.setProperty(PERSON_NAME, name)
    personNode.setProperty("level", level)
    personNode.setProperty("type", PERSON)
    personNode
  }

  protected override def persistToIndex(node: Node) = personIndex.add(node, PERSON_NAME, node.getProperty(PERSON_NAME))

  protected override def persistRelationships(relationships: List[Relation])  =
    relationships map { case (manager, reportee) => manager.createRelationshipTo(reportee, DIRECTLY_MANAGES) }

}
