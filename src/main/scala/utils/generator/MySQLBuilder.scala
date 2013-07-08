package utils.generator

import java.sql.Connection

private class MySQLBuilder (val mysql: Connection, val peopleAtLevels: Map[Int, List[String]]) {
   def build = ???
}