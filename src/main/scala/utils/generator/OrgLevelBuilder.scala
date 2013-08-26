package utils.generator

import utils.generator.DistributionStrategy._
import org.neo4j.unsafe.batchinsert.BatchInserter
import org.hibernate.SessionFactory

abstract class OrgLevelBuilder(val orgSize: Int, val level: Int, val distribution: DistributionStrategy)
  extends Builder with NamesGenerator {
  val neo4j: BatchInserter
  val sessionFactory: SessionFactory
  lazy val names = syntheticNames(orgSize)
  //lazy val names = naturalNames(orgSize)
  val orgDef: OrganizationDef

  override def build = new OrganizationBuilder(orgDef, distribution) with Neo4jBatchBuilderComponent with SQLBuilderComponent {
      val neo4jBatchBuilder = new Neo4jBatchBuilder(neo4j)
      val sqlBuilder = new SQLBuilder(sessionFactory)
    }.build
}
