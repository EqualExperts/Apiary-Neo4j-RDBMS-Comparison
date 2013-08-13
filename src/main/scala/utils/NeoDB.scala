package utils

import org.neo4j.cypher.{ExecutionResult, ExecutionEngine}
import org.neo4j.graphdb._
import org.neo4j.rest.graphdb.RestGraphDatabase
import scala.Predef._
import org.neo4j.graphdb.index.IndexHits
import java.util
import org.neo4j.unsafe.batchinsert.{BatchInserters, BatchInserterImpl}
import org.neo4j.kernel.impl.nioneo.store.FileSystemAbstraction
import org.neo4j.kernel.EmbeddedGraphDatabase
import org.neo4j.graphdb.factory.GraphDatabaseFactory

class NeoDB private (val neo4j: GraphDatabaseService) {

  val engine = new ExecutionEngine(neo4j)

  type Selector = RelationshipType => Boolean

  def usingTx(codeBlock: GraphDatabaseService => Unit) : Unit = {
    val transaction = neo4j.beginTx
    try {
      codeBlock(neo4j)
      transaction.success
    } catch  {
      case e : Exception => println("Transaction Failed: " +  e.getMessage)
        transaction.failure
    } finally {
      transaction.finish
    }
  }

  def createNode(name: String, aType: String, additionalProperties : Map[String, Any] = Map.empty[String, Any]) : Option[Node] = {
    var result : Option[Node] = None
    usingTx { db =>
      val node = db.createNode()
      node.setProperty("idxName", name)
      node.setProperty("type", aType)
      additionalProperties.foreach(kv => node.setProperty(kv._1, kv._2))
      println("Created node " + aType + " with idxName " + name)
      result = Option(node)
      println()
    }
    result
  }

  def createRelationship(from: Node, to: Node, relationshipType: RelationshipType, additionalProperties :  Map[String, Any] = Map.empty[String, Any]) : Relationship = {
    var relationship :Relationship = null
    usingTx { db =>
      relationship = from.createRelationshipTo(to, relationshipType)
      additionalProperties.foreach { kv => relationship.setProperty(kv._1, kv._2) }
    }
    relationship
  }

  def getNodeById(id: Long) = {
    try {
      Option(neo4j.getNodeById(id))
    }
    catch {
      case n: NotFoundException => None
    }
  }

  def getRelationshipById(id: Long) = {
    try {
      Option(neo4j.getRelationshipById(id))
    }
    catch {
      case n: NotFoundException => None
    }
  }

  def getNodeAutoIndexer = neo4j.index.getNodeAutoIndexer
  def getRelationshipAutoIndexer = neo4j.index.getRelationshipAutoIndexer
  def getIndexForNodes(idxName: String) = neo4j.index.forNodes(idxName)
  def getIndexForRelationships(idxName: String) = neo4j.index.forRelationships(idxName)
  def getGraphDatabaseService = neo4j
  def shutdown = neo4j.shutdown()

  def relationshipCount(node : Node, direction: Direction, types: RelationshipType*): Int = {
    var count = 0
    val relationshipIterator = node.getRelationships(direction, types: _*).iterator()
    while (relationshipIterator.hasNext) {
      relationshipIterator.next
      count = count + 1
    }
    count
  }


  def traverseDirectRelationships(aNode: Option[Node])(implicit canSelect: Selector = x => true) = aNode match {
    case None => Nil
    case Some(node) => {
      var mutableList = scala.collection.mutable.MutableList[Node]()
      val relationshipsIterator = node.getRelationships().iterator()
      while (relationshipsIterator.hasNext) {
        val relationship = relationshipsIterator.next
        if (canSelect(relationship.getType)) {
          mutableList += relationship.getEndNode
        }
      }
      mutableList.toList
    }
  }

  def execute(cql: String) = engine.execute(cql)
  def execute(cql: String, params: Map[String, Any]) = engine.execute(cql, params)

  def printRows(result : ExecutionResult) = {
    if (result.isEmpty) {
      println("NO ROWS FOUND")
    } else {
      //      println(result.dumpToString)
      for (row <- result) {
        val (_, value) = row.head
        if (row.size == 1) {
          value match {
            case r: Relationship => printf("Relationship: %s", r.getType.name)
            case n: Node => printf("%s: %s", n.getProperty("type"), n.getProperty("name"))
            case _ => printf("Row = %s", value)
          }
          println()
        } else {
          println("Row = " + row)
        }
      }
    }
  }

  def printNode(aNode: Option[Node]) = aNode match {
    case None => println ("No Node To Print")
    case Some(node) => {
      println("Node Id (System Generated): " + node.getId)
      val map = scala.collection.mutable.HashMap[String, Any]()
      val iterator = node.getPropertyKeys.iterator
      while(iterator hasNext) {
        val property = iterator next
        val value = node getProperty(property)
        map += (property -> value)
      }
      println("-- Node Properties:" + map.toString)

      if (node.hasRelationship()) {
        val relationships = node.getRelationships().iterator
        while (relationships.hasNext) {
          val relationship =  relationships.next
          println("-- Node Relationships:")
          printf("(%s) --[%s] --> (%s)", node.getId, relationship.getType(), relationship.getEndNode.getId)
          println("")
          println("\tRelationship Properties:")
          val relationshipProps = relationship.getPropertyKeys.iterator()
          while(relationshipProps.hasNext) {
            val key = relationshipProps.next
            printf("\tProperty = %s, Value = %s", key, relationship.getProperty(key))
          }
          println("")
        }
      }
      println("===========================================")
    }
  }

  def printRelationship(aRelationship: Option[Relationship]) = aRelationship match {
    case None => println("No Relationship to Print")
    case Some(r) => {
      printf("(%s)-[:%s]-(%s)", r.getStartNode.getId, r.getType.name, r.getEndNode.getId)
      println("\nRelationship Properties: ")
      val iterator: util.Iterator[String] = r.getPropertyKeys.iterator
      while (iterator.hasNext) {
        val key: String = iterator.next
        printf("%s = %s, ", key, r.getProperty(key))
      }
      println()
    }
  }

  def printNodeName(node: Node) =
    if (node == null) println("No Node to Print") else println(node.getProperty("idxName"))

  def printIndexHits(hits: IndexHits[_ <: AnyRef]) = {
    if(hits.size == 0) {
      println("No Index Hits to Print!!")
    } else {
      println("Printing Index Hits...")
      var count : Int = 1
      while(hits.hasNext) {
        println("===> HIT #" + count)
        hits.next match {
          case n: Node => printNode(Option(n))
          case r: Relationship => printRelationship(Option(r))
        }
        count = count + 1
      }
    }
  }
}

object Mode extends Enumeration {
  type Mode = Value
  val Embedded, Server = Value
}

import Mode._

object NeoDB {
  def apply(url: String, mode: Mode) = mode match {
    case Server => new NeoDB(new RestGraphDatabase(url))
    case Embedded => new NeoDB(new GraphDatabaseFactory().newEmbeddedDatabase(url))
    case _  => sys.error("Don't know how to process the Mode")
  }

  def apply(url: String, user: String, password: String) = new NeoDB(new RestGraphDatabase(url, user, password))
}

