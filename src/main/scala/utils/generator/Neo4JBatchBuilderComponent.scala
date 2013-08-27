package utils.generator

import org.neo4j.unsafe.batchinsert.BatchInserter
import org.neo4j.index.lucene.unsafe.batchinsert.LuceneBatchInserterIndexProvider
import org.neo4j.helpers.collection.MapUtil
import java.util

trait Neo4jBatchBuilderComponent extends Builder {
  self: OrganizationBuilder =>

  val neo4jBatchBuilder: Neo4jBatchBuilder

  override def build = {
    //    Runtime.getRuntime.addShutdownHook(new Thread {
    //      neo4jBatchBuilder.shutdown
    //    })
    try {
      super.build
      info("Building using Neo4j Batch Builder")
      neo4jBatchBuilder.build
    } finally {
      neo4jBatchBuilder.shutdownIndex
    }
  }

  class Neo4jBatchBuilder(val neo4j: BatchInserter)
    extends Neo4jBuilder[Long, Long](distributionStrategy, orgDef.peopleWithLevels, orgDef.withPersonManagingMaxOf)
    with EssentialQueries {

    private val indexProvider = new LuceneBatchInserterIndexProvider(neo4j)
    private val personIndex = createIndex(PERSON)

    protected override def createIndex(name: String) = {
      val personIndex = indexProvider.nodeIndex(name, MapUtil.stringMap("type", "exact"))
      personIndex.setCacheCapacity("name", 100000)
      personIndex
    }

    def shutdownIndex = {
      info("Shutting down index for %s", getClass.getSimpleName)
      indexProvider.shutdown
    }

    import collection.JavaConverters._

    protected override def persistNode(level: Int, name: String): Long = {
      val properties = Map[String, AnyRef]().asJava
      val node = neo4j.createNode(properties)
      neo4j.setNodeProperty(node, PERSON_NAME, name)
      neo4j.setNodeProperty(node, "level", level)
      neo4j.setNodeProperty(node, "type", PERSON)
      node
    }

    protected override def persistToIndex(node: Long) = {
      val personName = neo4j.getNodeProperties(node).get(PERSON_NAME)
      val propertiesToIndex = new util.HashMap[String, AnyRef]()
      propertiesToIndex.put(PERSON_NAME, personName)
      personIndex.add(node, propertiesToIndex)
    }

    protected override def persistRelationships(relationships: List[Relation]) =
      relationships map {
        case (manager, reportee) =>
          val properties = Map[String, AnyRef]().asJava
          neo4j.createRelationship(manager, reportee, DIRECTLY_MANAGES, properties)
      }

  }

}