package utils.generator

import org.neo4j.graphdb.RelationshipType
import utils.generator.DistributionStrategy._
import scala.annotation.tailrec

abstract class Neo4JBuilder[N, R](val peopleAtLevels: Map[Int, List[String]], val managingMax: Int) {
  val maxLevels = peopleAtLevels.size
  type Relation = (N, N)
  protected val PERSON = "Person"
  protected val PERSON_NAME = "name"


  protected object DIRECTLY_MANAGES extends RelationshipType {
    def name = getClass.getSimpleName.replace("$", "")
  }

  protected object INDIRECTLY_MANAGES extends RelationshipType {
    def name = getClass.getSimpleName.replace("$", "")
  }

  private def indexAll(nodesAtLevels: Map[Int, List[N]]) = {
    val nodes = nodesAtLevels.values
    nodes.flatten.foreach(persistToIndex)
  }

  private def makeRelationshipsBetween(nodesAtLevels: Map[Int, List[N]], useDistribution: DistributionStrategy): List[Relation] = {

    def between(parentNodes: List[N], childNodes: List[N]) : List[Relation] = {
      val manages = useDistribution match {
        case Contiguous => managingMax
        case Even =>   Math.min(Math.ceil(childNodes.length.toFloat/parentNodes.length).toInt, managingMax)
      }

      @tailrec
      def between0(acc: List[Relation], parentNodes: List[N], from: Int, to: Int): List[Relation] =
        parentNodes match {
          case Nil =>  acc
          case parent :: rest => {
            val children = childNodes.slice(from, to)
            val parentChildrenRelationships = children map { child => (parent, child) }
            between0(parentChildrenRelationships ::: acc, rest, from + manages, to + manages)
          }
        }
      between0(Nil, parentNodes, 0, manages)
    }

    @tailrec
    def relationships(acc: List[Relation], level: Int) : List[Relation] = {
      if (level == maxLevels) acc
      else {
        val parentNodes = nodesAtLevels(level)
        val childNodes  = nodesAtLevels(level + 1)
        relationships(between(parentNodes, childNodes) ::: acc, level + 1)
      }
    }
    relationships(Nil, level = 1)
  }

  private def persistNodes() =
    peopleAtLevels.map { case (level, people) =>
                            (level, people.map { persistNode (level, _) })
                  }.withDefaultValue(Nil)

  def build(useDistribution: DistributionStrategy) = {
    val people = measure("Persisting People", persistNodes)
    measure("Indexing People", indexAll, people)
    info("Creating Relationships using %s Distribution strategy", useDistribution)
    val managerReporteePairs = makeRelationshipsBetween(people, useDistribution)
    measure("Persisting Relationships", persistRelationships, managerReporteePairs)
    afterBuild
  }

  protected def afterBuild = {}
  protected def createIndex(name: String) : AnyRef
  protected def persistNode(level: Int, name: String): N
  protected def persistToIndex(node: N): Unit
  protected def persistRelationships(relationships: List[Relation]) : List[R]
}
