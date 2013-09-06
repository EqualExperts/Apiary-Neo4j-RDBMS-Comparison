package utils.generator

import org.neo4j.graphdb.{Relationship, Node}
import org.neo4j.rest.graphdb.RestGraphDatabase

trait Neo4jRestBuilderComponent extends Builder {
  self: OrganizationBuilder =>

  val neo4jRestBuilder: Neo4jRestBuilder

  override def build = {
    super.build
    info("Building using Neo4j Rest Builder")
    neo4jRestBuilder.build
    neo4jRestBuilder.shutdown
  }

  class Neo4jRestBuilder(val neo4jUrl: String)
  extends Neo4jBuilder[Node, Relationship](distributionStrategy,
                             orgDef.peopleWithLevels,
                             orgDef.withPersonManagingMaxOf) {

    val neo4j = new RestGraphDatabase(neo4jUrl)
    private val personIndex = createIndex(PERSON)
    protected override def createIndex(name: String) = neo4j.index.forNodes(name)

    protected override def persistNode(level: Int, name: String): Node = {
      val personNode = neo4j.createNode()
      personNode.setProperty("id", nextId)
      personNode.setProperty(PERSON_NAME, name)
      personNode.setProperty("level", level)
      personNode.setProperty("type", PERSON)
      personNode
    }

    protected override def persistToIndex(node: Node) = personIndex.add(node, PERSON_NAME, node.getProperty(PERSON_NAME))

    protected override def persistRelationships(relationships: List[Relation]) =
      relationships map {
        case (manager, reportee) => manager.createRelationshipTo(reportee, DIRECTLY_MANAGES)
      }

    def shutdown = neo4j.shutdown
  }
}