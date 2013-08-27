package utils.generator

import utils.generator.DistributionStrategy._
import scala.annotation.tailrec

abstract class DatabaseBuilder[N, R](val useDistribution: DistributionStrategy,
                                     val peopleAtLevels: Map[Int, List[String]],
                                     val managingMax: Int)
  extends Builder {

  val maxLevels = peopleAtLevels.size
  type Relation = (N, N)

  protected def makeRelationshipsBetween(nodesAtLevels: Map[Int, List[N]], useDistribution: DistributionStrategy): List[Relation] = {

    def between(parentNodes: List[N], childNodes: List[N]): List[Relation] = {
      val manages = useDistribution match {
        case Contiguous => managingMax
        case Even => Math.min(Math.ceil(childNodes.length.toFloat / parentNodes.length).toInt, managingMax)
      }

      @tailrec
      def between0(acc: List[Relation], parentNodes: List[N], from: Int, to: Int): List[Relation] =
        parentNodes match {
          case Nil => acc
          case parent :: rest => {
            val children = childNodes.slice(from, to)
            val parentChildrenRelationships = children map {
              child => (parent, child)
            }
            between0(parentChildrenRelationships ::: acc, rest, from + manages, to + manages)
          }
        }
      between0(Nil, parentNodes, 0, manages)
    }

    @tailrec
    def relationships(acc: List[Relation], level: Int): List[Relation] = {
      if (level == maxLevels) acc
      else {
        val parentNodes = nodesAtLevels(level)
        val childNodes = nodesAtLevels(level + 1)
        relationships(between(parentNodes, childNodes) ::: acc, level + 1)
      }
    }
    relationships(Nil, level = 1)
  }

  protected def persistNodes() =
    peopleAtLevels.map {
      case (level, people) =>
        (level, people.map {
          persistNode(level, _)
        })
    }.withDefaultValue(Nil)

  override def build = {
    val people = measure("Persisting People", persistNodes)
    info("Creating Relationships using %s Distribution strategy", useDistribution)
    val managerReporteePairs = makeRelationshipsBetween(people, useDistribution)
    measure("Persisting Relationships", persistRelationships, managerReporteePairs)
  }

  protected def persistNode(level: Int, name: String): N

  protected def persistRelationships(relationships: List[Relation]): List[R]
}
