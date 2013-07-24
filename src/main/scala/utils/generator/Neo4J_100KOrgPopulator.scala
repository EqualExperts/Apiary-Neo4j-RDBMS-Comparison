package utils.generator

import utils.{NeoDBBatchInserter, NeoDB}
import DistributionStrategy._
import org.neo4j.kernel.DefaultFileSystemAbstraction

/**
 * Note: You may need to enable and change the old value 64M of the wrapper.java.maxmemory property
 * in neo4j-wrapper.conf.
 *
 * # Maximum Java Heap Size (in MB)
 * wrapper.java.maxmemory=512
 */
object Neo4J_100KOrgPopulator extends App with NamesGenerator {
  override def main(args: Array[String]) = {
      val names = syntheticNames(100000)
//    val names = naturalNames(100000)

    /**
     * case 1:
     * total people in organisation = 100000, with Levels = 3, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *  At Level 1 => 4000
     *  At Level 2 => 16000
     *  At Level 3 => 80000
     *  Total => 100000
     */

     val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
                      .withPeopleAtLevel(1, 4000)
                      .withPeopleAtLevel(2, 16000)
                      .withPeopleAtLevel(3, 80000)
                      .distribute(Even)

    /**
     * case 2:
     * total people in organisation = 1000 with levels = 4, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     * At Level 1 => 10
     * At Level 2 => 50
     * At Level 3 => 200
     * At Level 4 => 740
     * Total => 1000
     */

//    val builder  = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
//      .withPeopleAtLevel(1, 10)
//      .withPeopleAtLevel(2, 43)
//      .withPeopleAtLevel(3, 200)
//      .withPeopleAtLevel(4, 747)
//      .distribute(Even)

    /**
     * case 3:
     * total people in organisation = 1000 with levels = 5, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *
     * At Level 1 => 3
     * At Level 2 => 15
     * At Level 3 => 75
     * At Level 4 => 300
     * At Level 5 => 607
     * Total => 1000
     */

    //    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
    //      .withPeopleAtLevel(1, 3)
    //      .withPeopleAtLevel(2, 15)
    //      .withPeopleAtLevel(3, 75)
    //      .withPeopleAtLevel(4, 300)
    //      .withPeopleAtLevel(5, 607)

    /**
     * case 4:
     * total people in organisation = 1000 with levels = 6, withPersonManagingMaxOf = 10, directlyReportingToMax = 1
     *
     * At Level 1 => 1
     * At Level 2 => 5
     * At Level 3 => 94
     * At Level 4 => 200
     * At Level 5 => 300
     * At Level 6 => 400
     * Total => 1000
     */

    //    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 10)
    //      .withPeopleAtLevel(1, 3)
    //      .withPeopleAtLevel(2, 15)
    //      .withPeopleAtLevel(3, 75)
    //      .withPeopleAtLevel(4, 300)
    //      .withPeopleAtLevel(5, 607)
    //      .distribute(Contiguous)

    /**
     * case 5:
     * total people in organisation = 1000 with levels = 6, withPersonManagingMaxOf = 10, directlyReportingToMax = 1
     *
     * At Level 1 => 1
     * At Level 2 => 5
     * At Level 3 => 50
     * At Level 4 => 94
     * At Level 5 => 150
     * At Level 6 => 250
     * At Level 7 => 450
     * Total => 1000
     */

//    val neoDb = NeoDB("http://localhost:7474/db/data")
    //	  val basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary"
    val neoDb = NeoDBBatchInserter(basePath + "/NEO4J_DATA/apiary_100k")

    builder buildWith neoDb
  }
}
