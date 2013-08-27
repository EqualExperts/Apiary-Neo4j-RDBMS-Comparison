package utils.generator

import utils.generator.DistributionStrategy._
import org.neo4j.graphdb.RelationshipType

abstract class Neo4jBuilder[N, R](override val useDistribution: DistributionStrategy,
                                  override val peopleAtLevels: Map[Int, List[String]],
                                  override val managingMax: Int)
  extends DatabaseBuilder[N, R](useDistribution, peopleAtLevels, managingMax) {

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

  override def build = {
    val people = measure("Persisting People", persistNodes)
    measure("Indexing People", indexAll, people)
    info("Creating Relationships using %s Distribution strategy", useDistribution)
    val managerReporteePairs = makeRelationshipsBetween(people, useDistribution)
    measure("Persisting Relationships", persistRelationships, managerReporteePairs)
  }

  protected def createIndex(name: String): AnyRef

  protected def persistToIndex(node: N): Unit

}
