package utils.generator

import org.neo4j.graphdb.{RelationshipType}
import utils.generator.DistributionStrategy._
import scala.annotation.tailrec
import org.neo4j.unsafe.batchinsert.BatchInserter
import org.neo4j.index.lucene.unsafe.batchinsert.LuceneBatchInserterIndexProvider
import org.neo4j.helpers.collection.MapUtil
import java.util

private class Neo4JBatchBuilder (val neo4j: BatchInserter, val peopleAtLevels: Map[Int, List[String]], val managingMax: Int) {

  type Relationship = (Long, Long)
  val maxLevels = peopleAtLevels.size
  private val indexProvider = new LuceneBatchInserterIndexProvider(neo4j)
  private val PERSON = "Person"
  private val PERSON_NAME = "name"
  private val personIndex = createIndex(PERSON)

  private def createIndex(name: String) = {
    val personIndex = indexProvider.nodeIndex(name, MapUtil.stringMap("type", "exact"))
    personIndex.setCacheCapacity("name", 100000)
    personIndex
  }

  private def shutdownIndex = indexProvider.shutdown

  private object DIRECTLY_MANAGES extends RelationshipType {
    def name = getClass.getSimpleName.replace("$", "")
  }

  private object INDIRECTLY_MANAGES extends RelationshipType {
    def name = getClass.getSimpleName.replace("$", "")
  }

  import collection.JavaConverters._
  private def toNode(neo4j: BatchInserter, level: Int, name: String): Long = {
    val properties = Map[String, AnyRef]().asJava
    val node = neo4j createNode properties
    neo4j.setNodeProperty(node, PERSON_NAME, name)
    neo4j.setNodeProperty(node, "level", level)
    neo4j.setNodeProperty(node, "type", PERSON)
    node
  }

  private def index(node: Long) = {
    val personName = neo4j.getNodeProperties(node).get(PERSON_NAME)
    val propertiesToIndex = new util.HashMap[String, AnyRef]()
    propertiesToIndex.put(PERSON_NAME, personName)
    personIndex.add(node, propertiesToIndex)
  }

  private def indexAll(nodesAtLevels: Map[Int, List[Long]]) = nodesAtLevels.values.flatten.foreach(index)

  private def persistNodes(graphDb: BatchInserter) =
    peopleAtLevels map {
      case (level, people) => (level, people map { toNode (graphDb, level, _) })
    } withDefaultValue Nil

  private def persistRelationships(relationships: List[Relationship]) =
    relationships map { case (manager, reportee) => val properties = Map[String, AnyRef]().asJava
      neo4j.createRelationship(manager, reportee, DIRECTLY_MANAGES, properties) }

  private def makeRelationshipsBetween(nodesAtLevels: Map[Int, List[Long]], distributionStrategy: DistributionStrategy): List[Relationship] = {

    def parentToChildrenRelationships(parent: Long, children: List[Long]): List[Relationship] =
      for (child <- children) yield (parent, child)

    def between(parentNodes: List[Long], childNodes: List[Long]) : List[Relationship] = {
      val manages = distributionStrategy match {
        case Contiguous => managingMax
        case Even =>   Math.min(Math.ceil(childNodes.length.toFloat/parentNodes.length).toInt, managingMax)
      }

      @tailrec
      def between0(acc: List[Relationship], parentNodes: List[Long], from: Int, to: Int): List[Relationship] =
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
    measure("Indexing People", indexAll, people)
    info("Creating Relationships using %s Distribution strategy", distributionStrategy)
    val managerReporteePairs = makeRelationshipsBetween(people, distributionStrategy)
    measure("Persisting Relationships", persistRelationships, managerReporteePairs)
    shutdownIndex
    info("PLEASE DELETE NODE WITH ID 0 MANUALLY!!!")
  }
}
