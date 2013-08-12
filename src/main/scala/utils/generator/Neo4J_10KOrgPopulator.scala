package utils.generator

import utils.{NeoDBBatchInserter, NeoDB}
import DistributionStrategy._
import org.neo4j.kernel.DefaultFileSystemAbstraction

object Neo4J_10KOrgPopulator extends App with NamesGenerator {
  override def main(args: Array[String]) = {
    val names = syntheticNames(10000)
//    val names = naturalNames(10000)

    /**
     * case 1:
     * total people in organisation = 10000, with Levels = 3, withPersonManagingMaxOf = 100, directlyReportingToMax = 1
     *  At Level 1 => 4
     *  At Level 2 => 200
     *  At Level 3 => 9796
     *  Total => 10000
     */

//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 100)
//         .withPeopleAtLevel(1, 4)
//         .withPeopleAtLevel(2, 200)
//         .withPeopleAtLevel(3, 9796)
//         .distribute(Contiguous)

    /**
     * case 2:
     * total people in organisation = 10000 with levels = 4, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     * At Level 1 => 2
     * At Level 2 => 50
     * At Level 3 => 2500
     * At Level 4 => 7448
     * Total => 10000
     */

//    val builder  = OrganizationBuilder(names, withPersonManagingMaxOf = 50)
//        .withPeopleAtLevel(1, 2)
//        .withPeopleAtLevel(2, 50)
//        .withPeopleAtLevel(3, 2500)
//        .withPeopleAtLevel(4, 7448)
//        .distribute(Contiguous)

    /**
     * case 3:
     * total people in organisation = 10000 with levels = 5, withPersonManagingMaxOf = 50, directlyReportingToMax = 1
     *
     * At Level 1 => 2
     * At Level 2 => 50
     * At Level 3 => 250
     * At Level 4 => 1250
     * At Level 5 => 8448
     * Total => 10000
     */

//        val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 50)
//          .withPeopleAtLevel(1, 2)
//          .withPeopleAtLevel(2, 50)
//          .withPeopleAtLevel(3, 250)
//          .withPeopleAtLevel(4, 1250)
//          .withPeopleAtLevel(5, 8448)

    /**
     * case 4:
     * total people in organisation = 10000 with levels = 6, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *
     * At Level 1 => 30
     * At Level 2 => 100
     * At Level 3 => 500
     * At Level 4 => 1000
     * At Level 5 => 3000
     * At Level 6 => 5370
     * Total => 10000
     */

//        val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 25)
//          .withPeopleAtLevel(1, 2)
//          .withPeopleAtLevel(2, 20)
//          .withPeopleAtLevel(3, 250)
//          .withPeopleAtLevel(4, 1000)
//          .withPeopleAtLevel(5, 3000)
//          .withPeopleAtLevel(6, 5728)
//          .distribute(Contiguous)
//
    /**
     * case 5:
     * total people in organisation = 10000 with levels = 7, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *
     * At Level 1 => 20
     * At Level 2 => 50
     * At Level 3 => 200
     * At Level 4 => 700
     * At Level 5 => 1400
     * At Level 6 => 2800
     * At Level 7 => 4830
     * Total => 10000
     */

        val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 20)
          .withPeopleAtLevel(1, 2)
          .withPeopleAtLevel(2, 22)
          .withPeopleAtLevel(3, 300)
          .withPeopleAtLevel(4, 700)
          .withPeopleAtLevel(5, 1300)
          .withPeopleAtLevel(6, 2800)
          .withPeopleAtLevel(7, 4876)
//          .distribute(Contiguous)

    /**
     * case 6:
     * total people in organisation = 10000 with levels = 8, withPersonManagingMaxOf = 50, directlyReportingToMax = 1
     *
     * At Level 1 => 4
     * At Level 2 => 20
     * At Level 3 => 80
     * At Level 4 => 250
     * At Level 5 => 700
     * At Level 6 => 1400
     * At Level 7 => 2500
     * At Level 8 => 5046
     * Total => 10000
     */

//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 50)
//      .withPeopleAtLevel(1, 4)
//      .withPeopleAtLevel(2, 20)
//      .withPeopleAtLevel(3, 80)
//      .withPeopleAtLevel(4, 250)
//      .withPeopleAtLevel(5, 700)
//      .withPeopleAtLevel(6, 1400)
//      .withPeopleAtLevel(7, 2500)
//      .withPeopleAtLevel(8, 5046)
//      .distribute(Contiguous)

//    val neoDb = NeoDB("http://localhost:7474/db/data")
    //	  val basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable"
    val neoDb = NeoDBBatchInserter(basePath + "/NEO4J_DATA/apiary_10k_l7")

    builder buildWith neoDb
  }
}
