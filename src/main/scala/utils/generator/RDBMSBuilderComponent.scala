package utils.generator

import org.hibernate.SessionFactory
import org.hibernate.cfg.AnnotationConfiguration
import domain.{DirectManager, Person}

object SQLDatabase extends Enumeration {
  type SQLDatabase = Value
  val MySQL, MSSQL = Value
}

trait RDBMSBuilderComponent extends Builder {
  self: OrganizationBuilder =>

  val rdbmsBuilder: RDBMSBuilder

  override def build = {
    super.build
    info("Building using RDBMSBuilder")
    rdbmsBuilder.build
  }

  import SQLDatabase._

  class RDBMSBuilder(databases: (SQLDatabase, String)*) extends Builder {
    override def build =
      databases map {
        case (database, hibernateConfig) =>
          info("Building for %s", database)
          val sessionFactory = new AnnotationConfiguration().configure(hibernateConfig).buildSessionFactory
          withinTxn(new SQLBuilder(sessionFactory))
      }

    private def withinTxn(sqlBuilder: SQLBuilder) = {
      val transaction = sqlBuilder.session.beginTransaction
      try {
        sqlBuilder.build
        transaction.commit
      } catch {
        case e: Throwable => transaction.rollback
      } finally {
        sqlBuilder.shutdown
      }
    }
  }

  private class SQLBuilder(val sessionFactory: SessionFactory)
    extends DatabaseBuilder[Person, DirectManager](distributionStrategy, orgDef.peopleWithLevels, orgDef.withPersonManagingMaxOf) {
    var records = 0
    val session = sessionFactory.openSession
    val peopleWithLevels = orgDef.peopleWithLevels


    def shutdown = {
      session.flush
      session.close
      info("Shutting down %s", getClass.getSimpleName)
    }

    //TODO: Make sure this works for high volume by introducng batch flush
    protected def persistNode(level: Int, name: String): Person = {
      val person = new Person(name, level)
      session.save(person)
      flushSessionIfRequired
      person
    }

    private def flushSessionIfRequired =
      if (records % 40 == 0) {
        session.flush
        session.clear
        records = 0
      }
      else records += 1

    protected def persistRelationships(relationships: List[Relation]): List[DirectManager] = {
      relationships map {
        case (manager, reportee) =>
          val relationship = new DirectManager(manager, reportee)
          session.save(relationship)
          flushSessionIfRequired
          relationship
      }
    }
  }

}
