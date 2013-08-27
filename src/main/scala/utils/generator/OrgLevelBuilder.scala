package utils.generator

import utils.generator.DistributionStrategy._
import org.neo4j.unsafe.batchinsert.BatchInserter
import utils.{Mode, NeoDB}

//TODO: 1. closing the Neo4j connection early
abstract class OrgLevelBuilder(val orgSize: Int, val level: Int, val usingDistribution: DistributionStrategy)
  extends Builder with NamesGenerator with EssentialQueries {
  val neo4j: BatchInserter
  lazy val names = syntheticNames(orgSize)
  //lazy val names = naturalNames(orgSize)
  val orgDef: OrganizationDef

  import SQLDatabase._

  override def build = {
    val builder = new OrganizationBuilder(orgDef, usingDistribution) with Neo4jBatchBuilderComponent with RDBMSBuilderComponent {
      val neo4jBatchBuilder = new Neo4jBatchBuilder(neo4j)
      val rdbmsBuilder = new RDBMSBuilder(MySQL -> "hibernate-mysql.cfg.xml",
        MSSQL -> "hibernate-mssql.cfg.xml")
    }
    //Try Building...
    try {
      builder build
    } finally {
      info("Shutting down %s", neo4j.getClass.getSimpleName)
      neo4j.shutdown
    }
    deleteNeo4jRefNode
  }

  private def deleteNeo4jRefNode {
    val storeDir = neo4j.getStoreDir
    info("Opening connection to Neo4j DB at : %s to delete reference node", storeDir)
    import Mode._
    implicit val neo4jDatabase = NeoDB(storeDir, Embedded)
    deleteRefNodeIfPresent
    info("Shutting Neo4j DB at : %s after deleting reference node", storeDir)
    neo4jDatabase.shutdown
  }
}
