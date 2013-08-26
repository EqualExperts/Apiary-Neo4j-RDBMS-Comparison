package utils.generator

import com.ee.apiary.sql.hibernate.entities.{Person, DirectManager}
import org.hibernate.SessionFactory

trait SQLBuilderComponent extends Builder {
  self: OrganizationBuilder =>

  val sqlBuilder: SQLBuilder

  override def build = {
    val transaction = sqlBuilder.session.beginTransaction
    try {
      super.build
      info("Building using SQLBuilder")
      sqlBuilder.build
      transaction.commit
    } catch {
      case e : Throwable => transaction.rollback
    } finally {
      sqlBuilder.shutdown
    }
  }

  class SQLBuilder (val sessionFactory: SessionFactory)
  extends DatabaseBuilder[Person, DirectManager](distributionStrategy, orgDef.peopleWithLevels, orgDef.withPersonManagingMaxOf) {
    var records = 0
    val session = sessionFactory.openSession
    val peopleWithLevels = orgDef.peopleWithLevels


    def shutdown = {
      session.flush
      session.close
      info("Shutting down %s", getClass.getSimpleName)
    }

    protected def createIndex(name: String): AnyRef = {
      info("For SQLBuilder Indexes are created using Scripts.  Nothing to do here!")
      None
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

    protected def persistToIndex(node: Person) {
      info("For SQLBuilder Indexes are created using Scripts.  Nothing to do here!")
    }

    protected def persistRelationships(relationships: List[Relation]): List[DirectManager] = {
      relationships map { case(manager, reportee) =>
        val relationship = new DirectManager(manager, reportee)
        session.save(relationship)
        flushSessionIfRequired
        relationship
      }
    }
  }
}
