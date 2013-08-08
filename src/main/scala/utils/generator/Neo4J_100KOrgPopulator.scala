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
     * total people in organisation = 100000, with Levels = 3, withPersonManagingMaxOf = 500, directlyReportingToMax = 1
     *  At Level 1 => 10
     *  At Level 2 => 1000
     *  At Level 3 => 98990
     *  Total => 100000
     */

//     val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 500)
//          .withPeopleAtLevel(1, 10)
//          .withPeopleAtLevel(2, 1000)
//          .withPeopleAtLevel(3, 98990)
//          .distribute(Contiguous)

    /**
     * case 2:
     * total people in organisation = 1000 with levels = 4, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     * At Level 1 => 1000
     * At Level 2 => 5000
     * At Level 3 => 20000
     * At Level 4 => 74000
     * Total => 100000
     */

//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
//      .withPeopleAtLevel(1, 1000)
//      .withPeopleAtLevel(2, 500000000)
//      .withPeopleAtLevel(3, 20000)
//      .withPeopleAtLevel(4, 74000)
//      .distribute(Even)

    /**
     * case 3:
     * total people in organisation = 100000 with levels = 5, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *
     * At Level 1 => 300
     * At Level 2 => 1500
     * At Level 3 => 7500
     * At Level 4 => 30000
     * At Level 5 => 60700
     * Total => 100000
     */

//        val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
//          .withPeopleAtLevel(1, 300)
//          .withPeopleAtLevel(2, 1500)
//          .withPeopleAtLevel(3, 7500)
//          .withPeopleAtLevel(4, 30000)
//          .withPeopleAtLevel(5, 60700)

    /**
     * case 4:
     * total people in organisation = 100000 with levels = 6, withPersonManagingMaxOf = 10, directlyReportingToMax = 1
     *
     * At Level 1 => 2
     * At Level 2 => 20
     * At Level 3 => 100
     * At Level 4 => 1000
     * At Level 5 => 10000
     * At Level 6 => 88878
     * Total => 100000
     */

//        val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 10)
//          .withPeopleAtLevel(1, 2)
//          .withPeopleAtLevel(2, 20)
//          .withPeopleAtLevel(3, 100)
//          .withPeopleAtLevel(4, 1000)
//          .withPeopleAtLevel(5, 10000)
//          .withPeopleAtLevel(6, 88878)
//          .distribute(Contiguous)

    /**
     * case 5:
     * total people in organisation = 100000 with levels = 7, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *
     * At Level 1 => 6
     * At Level 2 => 29
     * At Level 3 => 143
     * At Level 4 => 711
     * At Level 5 => 3555
     * At Level 6 => 17775
     * At Level 7 => 77781
     * Total => 100000
     */

//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
//      .withPeopleAtLevel(1, 6)
//      .withPeopleAtLevel(2, 29)
//      .withPeopleAtLevel(3, 143)
//      .withPeopleAtLevel(4, 711)
//      .withPeopleAtLevel(5, 3555)
//      .withPeopleAtLevel(6, 17775)
//      .withPeopleAtLevel(7, 77781)

    /**
     * case 6:
     * total people in organisation = 100000 with levels = 7, withPersonManagingMaxOf = 4, directlyReportingToMax = 1
     *
     * At Level 1 => 2
     * At Level 2 => 15
     * At Level 3 => 100
     * At Level 4 => 500
     * At Level 5 => 1500
     * At Level 6 => 8000
     * At Level 7 => 19883
     * At Level 8 => 70000
     * Total => 100000
     */

    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 50)
      .withPeopleAtLevel(1, 2)
      .withPeopleAtLevel(2, 15)
      .withPeopleAtLevel(3, 100)
      .withPeopleAtLevel(4, 500)
      .withPeopleAtLevel(5, 1500)
      .withPeopleAtLevel(6, 8000)
	    .withPeopleAtLevel(7, 19883)
	    .withPeopleAtLevel(8, 70000)

    //    val neoDb = NeoDB("http://localhost:7474/db/data")
    //	  val basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable"
    val neoDb = NeoDBBatchInserter(basePath + "/NEO4J_DATA/apiary_100k_l8")

    builder buildWith neoDb
  }
}
