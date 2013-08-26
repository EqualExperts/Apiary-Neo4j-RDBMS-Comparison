package utils.generator

import org.neo4j.graphdb.{Relationship, Node, GraphDatabaseService}

trait Neo4jRestBuilderComponent extends Builder {
  self: OrganizationBuilder =>

  val neo4jRestBuilder: Neo4jRestBuilder

  override def build = {
    try {
      super.build
      info("Building using Neo4j Rest Builder")
      neo4jRestBuilder.build
    } finally {
      neo4jRestBuilder.shutdown
    }
  }

  class Neo4jRestBuilder (val neo4j: GraphDatabaseService)
  extends Neo4JBuilder[Node, Relationship](distributionStrategy, orgDef.peopleWithLevels, orgDef.withPersonManagingMaxOf) {

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

    def shutdown = {
      info("Shutting down %s", getClass.getSimpleName)
      neo4j.shutdown
    }
  }
}