package utils.generator

import utils.{NeoDBBatchInserter, NeoDB}
import DistributionStrategy._
import org.neo4j.kernel.DefaultFileSystemAbstraction

object Neo4J_10KOrgPopulator extends App with NamesGenerator {
  override def main(args: Array[String]) = {
    //    val names = syntheticNames(10000)
    val names = naturalNames(10000)

    /**
     * case 1:
     * total people in organisation = 10000, with Levels = 3, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *  At Level 1 => 400
     *  At Level 2 => 1600
     *  At Level 3 => 8000
     *  Total => 10000
     */

    //val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
    //                   .withPeopleAtLevel(1, 400)
    //                   .withPeopleAtLevel(2, 1600)
    //                   .withPeopleAtLevel(3, 8000)
    //                   .distribute(Contiguous)

    /**
     * case 2:
     * total people in organisation = 10000 with levels = 4, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     * At Level 1 => 100
     * At Level 2 => 500
     * At Level 3 => 2000
     * At Level 4 => 7400
     * Total => 10000
     */

    val builder  = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
                      .withPeopleAtLevel(1, 100)
                      .withPeopleAtLevel(2, 500)
                      .withPeopleAtLevel(3, 2000)
                      .withPeopleAtLevel(4, 7400)
                      .distribute(Contiguous)

    /**
     * case 3:
     * total people in organisation = 10000 with levels = 5, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *
     * At Level 1 => 30
     * At Level 2 => 150
     * At Level 3 => 750
     * At Level 4 => 3000
     * At Level 5 => 6070
     * Total => 10000
     */

    //    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
    //      .withPeopleAtLevel(1, 30)
    //      .withPeopleAtLevel(2, 150)
    //      .withPeopleAtLevel(3, 750)
    //      .withPeopleAtLevel(4, 3000)
    //      .withPeopleAtLevel(5, 6070)

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

    //    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
    //      .withPeopleAtLevel(1, 30)
    //      .withPeopleAtLevel(2, 100)
    //      .withPeopleAtLevel(3, 500)
    //      .withPeopleAtLevel(4, 1000)
    //      .withPeopleAtLevel(5, 3000)
    //      .withPeopleAtLevel(6, 5370)
    //      .distribute(Contiguous)

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

    //    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
    //      .withPeopleAtLevel(1, 20)
    //      .withPeopleAtLevel(2, 50)
    //      .withPeopleAtLevel(3, 200)
    //      .withPeopleAtLevel(4, 700)
    //      .withPeopleAtLevel(5, 1400)
    //      .withPeopleAtLevel(6, 2800)
    //      .withPeopleAtLevel(7, 4830)
    //      .distribute(Contiguous)

    /**
     * case 6:
     * total people in organisation = 10000 with levels = 8, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
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

    //    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
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
    val basePath = "/Users/dhavald/Documents/workspace/Apiary"
    val neoDb = NeoDBBatchInserter(basePath + "/NEO4J_DATA/apiary_10k")

    builder buildWith neoDb
  }
}
