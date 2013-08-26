package utils.generator

import utils.generator.DistributionStrategy._
import org.neo4j.unsafe.batchinsert.BatchInserter

abstract class OrgLevelBuilder(val orgSize: Int, val level: Int, val usingDistribution: DistributionStrategy)
  extends Builder with NamesGenerator {
  val neo4j: BatchInserter
  lazy val names = syntheticNames(orgSize)
  //lazy val names = naturalNames(orgSize)
  val orgDef: OrganizationDef

  import SQLDatabase._

  override def build =
    new OrganizationBuilder(orgDef, usingDistribution) with Neo4jBatchBuilderComponent with RDBMSBuilderComponent {
      val neo4jBatchBuilder = new Neo4jBatchBuilder(neo4j)
      val rdbmsBuilder = new RDBMSBuilder(MySQL -> "hibernate-mysql.cfg.xml",
                                      MSSQL -> "hibernate-mssql.cfg.xml")
    }.build
}
