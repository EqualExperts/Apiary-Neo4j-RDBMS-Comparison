package utils.generator

import utils.NeoDB
import DistributionStrategy._

/**
 *
 * Note: You may need to enable and change the old value 64M of the wrapper.java.maxmemory property
 * in neo4j-wrapper.conf.
 *
 * # Maximum Java Heap Size (in MB)
 * wrapper.java.maxmemory=1024
 *
 * Also enable memory mapped regions in neo4j.properties
 * neostore.nodestore.db.mapped_memory=512M
 * neostore.relationshipstore.db.mapped_memory=512M
 * neostore.propertystore.db.mapped_memory=512M
 *
 * PLEASE NOTE THIS WILL TAKE HOURS TO RUN, SO LEAVE IT OVERNIGHT AND COME BACK NEXT DAY!!!
 *
 * [Wed Jul 17 11:05:19 IST 2013] [INFO]: Total in Org = 1000000 people
 *
 * [Wed Jul 17 11:05:19 IST 2013] [INFO]: Persisting People...
 * [Wed Jul 17 13:47:48 IST 2013] [INFO]: Persisting People...Complete. Execution Time 9749375(ms) =~ 9749.375(secs)
 * [Wed Jul 17 13:47:48 IST 2013] [INFO]: Indexing People...
 * [Wed Jul 17 15:48:32 IST 2013] [INFO]: Indexing People...Complete. Execution Time 7243980(ms) =~ 7243.980(secs)
 * [Wed Jul 17 15:48:32 IST 2013] [INFO]: Creating Relationships using Even Distribution strategy
 * [Wed Jul 17 16:23:03 IST 2013] [INFO]: Persisting Relationships...
 *
 *
 */
object Neo4J_1MOrgPopulator extends App with NamesGenerator {
  override def main(args: Array[String]) = {
    val names = syntheticNames(1000000)
    //    val names = naturalNames(1000000)

    /**
     * case 1:
     * total people in organisation = 1000000, with Levels = 6, withPersonManagingMaxOf = 10, directlyReportingToMax = 1
     *  At Level 1 => 10
     *  At Level 2 => 100
     *  At Level 3 => 1000
     *  At Level 4 => 10000
     *  At Level 5 => 100000
     *  At Level 6 => 888890
     *  Total => 1000000
     */

    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 10)
      .withPeopleAtLevel(1, 10)
      .withPeopleAtLevel(2, 100)
      .withPeopleAtLevel(3, 1000)
      .withPeopleAtLevel(4, 10000)
      .withPeopleAtLevel(5, 100000)
      .withPeopleAtLevel(6, 888890)
      .distribute(Even)

    val neoDb = NeoDB("http://localhost:7474/db/data")
    builder buildWith neoDb

  }
}
