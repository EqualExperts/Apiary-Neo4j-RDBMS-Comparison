name := """Neo4j Apiary Application"""

version := "0.1"

scalaVersion := "2.10.1"

resolvers += "Neo4J Snapshots and Versions" at "http://m2.neo4j.org/content/groups/everything"

//resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"

libraryDependencies ++= Seq(
    "org.neo4j" % "neo4j" % "1.9",
    "org.neo4j" % "neo4j-rest-graphdb" % "1.9",
    "org.specs2" %% "specs2" % "2.1-SNAPSHOT" % "test"
)
