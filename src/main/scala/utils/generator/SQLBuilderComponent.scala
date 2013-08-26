package utils.generator

import java.sql.Connection

object SqlDatabase extends Enumeration {
  type SqlDatabase = Value
  val MySQL, MSSQL = Value
}

trait SQLBuilderComponent extends Builder {
  self: OrganizationBuilder =>

  val sqlBuilder: SQLBuilder

  override def build = {
    try {
      super.build
      info("Building using SQLBuilder")
      sqlBuilder.build
    } finally {
      sqlBuilder.shutdown
    }
  }

  import SqlDatabase._

  class SQLBuilder (val sql: Connection, val databaseType: SqlDatabase = MySQL) {
    val peopleWithLevels = orgDef.peopleWithLevels
    def build = info("Building SQL Database")
    def shutdown = info("Shutting down %s", getClass.getSimpleName)
  }
}
