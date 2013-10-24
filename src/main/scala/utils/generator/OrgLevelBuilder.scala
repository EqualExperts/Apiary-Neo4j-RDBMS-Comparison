package utils.generator

import utils.generator.DistributionStrategy._
import utils.{Mode, NeoDB}

abstract class OrgLevelBuilder(val orgSize: Int, val level: Int, val usingDistribution: DistributionStrategy)
  extends Builder with NamesGenerator with EssentialQueries {
  val neo4jStoreDir: String
  //lazy val names = syntheticNames(orgSize)
  lazy val names = naturalNames(orgSize)
  val orgDef: OrganizationDef

  import SQLDatabase._

  override def build = {
  /*  val builder = new OrganizationBuilder(orgDef, usingDistribution) with Neo4jBatchBuilderComponent with RDBMSBuilderComponent {
      val neo4jBatchBuilder = new Neo4jBatchBuilder(neo4jStoreDir)
      val rdbmsBuilder = new RDBMSBuilder(MySQL -> "hibernate-mysql.cfg.xml",
                                          MSSQL -> "hibernate-mssql.cfg.xml")
  }
  */
  val builder = new OrganizationBuilder(orgDef, usingDistribution) with Neo4jRestBuilderComponent {
    val neo4jRestBuilder = new Neo4jRestBuilder(neo4jStoreDir)
  }
    builder.build
    deleteNeo4jRefNode
  }

  private def deleteNeo4jRefNode = {
    info("Opening connection to Neo4j DB at : %s to delete reference node", neo4jStoreDir)
    import Mode._
    implicit val neo4jDatabase = NeoDB(neo4jStoreDir, Embedded)
    deleteRefNodeIfPresent
    info("Shutting Neo4j DB at : %s after deleting reference node", neo4jStoreDir)
    neo4jDatabase.shutdown
  }
}
