package utils.generator

import org.neo4j.unsafe.batchinsert.BatchInserter
import org.neo4j.index.lucene.unsafe.batchinsert.LuceneBatchInserterIndexProvider
import org.neo4j.helpers.collection.MapUtil
import java.util

private class Neo4JBatchBuilder (val neo4j: BatchInserter,
                                 override val peopleAtLevels: Map[Int, List[String]],
                                 override val managingMax: Int)
  extends Neo4JBuilder[Long, Long](peopleAtLevels, managingMax) {

  private val indexProvider = new LuceneBatchInserterIndexProvider(neo4j)
  private val personIndex = createIndex(PERSON)

  protected override def createIndex(name: String) = {
    val personIndex = indexProvider.nodeIndex(name, MapUtil.stringMap("type", "exact"))
    personIndex.setCacheCapacity("name", 100000)
    personIndex
  }

  private def shutdownIndex = indexProvider.shutdown

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
    relationships map { case (manager, reportee) => val properties = Map[String, AnyRef]().asJava
      neo4j.createRelationship(manager, reportee, DIRECTLY_MANAGES, properties) }


  protected override def afterBuild = {
    shutdownIndex
    info("PLEASE DELETE NODE WITH ID 0 MANUALLY!!!")
  }
}
