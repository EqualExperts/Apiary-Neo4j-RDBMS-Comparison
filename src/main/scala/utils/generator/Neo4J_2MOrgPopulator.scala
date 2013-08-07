package utils.generator

import utils.{NeoDBBatchInserter, NeoDB}
import DistributionStrategy._
import org.neo4j.kernel.DefaultFileSystemAbstraction

object Neo4J_2MOrgPopulator extends App with NamesGenerator {
  override def main(args: Array[String]) = {
        val names = syntheticNames(2000000)
//    val names = naturalNames(2000000)

    /**
     * case 1:
     * total people in organisation = 2000000, with Levels = 3, withPersonManagingMaxOf = 1000, directlyReportingToMax = 1
     *  At Level 1 => 10
     *  At Level 2 => 10000
     *  At Level 3 => 1989990
     *  Total => 2000000
     */

//        val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 1000)
//                           .withPeopleAtLevel(1, 10)
//                           .withPeopleAtLevel(2, 10000)
//                           .withPeopleAtLevel(3, 1989990)
//                           .distribute(Contiguous)

    /**
     * case 2:
     * total people in organisation = 2000000 with levels = 4, withPersonManagingMaxOf = 500, directlyReportingToMax = 1
     * At Level 1 => 5
     * At Level 2 => 25
     * At Level 3 => 4000
     * At Level 4 => 1995970
     * Total => 2000000
     */

//        val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 500)
//                          .withPeopleAtLevel(1, 5)
//                          .withPeopleAtLevel(2, 25)
//                          .withPeopleAtLevel(3, 4000)
//                          .withPeopleAtLevel(4, 1995970)
//                          .distribute(Contiguous)

    /**
     * case 3:
     * total people in organisation = 2000000 with levels = 5, withPersonManagingMaxOf = 500, directlyReportingToMax = 1
     *
     * At Level 1 => 3
     * At Level 2 => 15
     * At Level 3 => 400
     * At Level 4 => 4000
     * At Level 5 => 1995582
     * Total => 2000000
     */

//            val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 500)
//              .withPeopleAtLevel(1, 3)
//              .withPeopleAtLevel(2, 15)
//              .withPeopleAtLevel(3, 400)
//              .withPeopleAtLevel(4, 4000)
//              .withPeopleAtLevel(5, 1995582)

    /**
     * case 4:
     * total people in organisation = 2000000 with levels = 6, withPersonManagingMaxOf = 500, directlyReportingToMax = 1
     *
     * At Level 1 => 3
     * At Level 2 => 10
     * At Level 3 => 50
     * At Level 4 => 400
     * At Level 5 => 20000
     * At Level 6 => 1979537
     * Total => 2000000
     */

//            val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 500)
//              .withPeopleAtLevel(1, 3)
//              .withPeopleAtLevel(2, 10)
//              .withPeopleAtLevel(3, 50)
//              .withPeopleAtLevel(4, 400)
//              .withPeopleAtLevel(5, 20000)
//              .withPeopleAtLevel(6, 1979537)
//              .distribute(Contiguous)

    /**
     * case 5:
     * total people in organisation = 2000000 with levels = 7, withPersonManagingMaxOf = 100, directlyReportingToMax = 1
     *
     * At Level 1 => 2
     * At Level 2 => 50
     * At Level 3 => 200
     * At Level 4 => 1000
     * At Level 5 => 10000
     * At Level 6 => 100000
     * At Level 7 => 1888748
     * Total => 2000000
     */

            val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 100)
              .withPeopleAtLevel(1, 2)
              .withPeopleAtLevel(2, 50)
              .withPeopleAtLevel(3, 200)
              .withPeopleAtLevel(4, 1000)
              .withPeopleAtLevel(5, 10000)
              .withPeopleAtLevel(6, 100000)
              .withPeopleAtLevel(7, 1888748)
              .distribute(Contiguous)

    /**
     * case 6:
     * total people in organisation = 2000000 with levels = 8, withPersonManagingMaxOf = 50, directlyReportingToMax = 1
     *
     * At Level 1 => 2
     * At Level 2 => 20
     * At Level 3 => 100
     * At Level 4 => 500
     * At Level 5 => 5000
     * At Level 6 => 10000
     * At Level 7 => 50000
     * At Level 8 => 1934378
     * Total => 2000000
     */

//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 50)
//      .withPeopleAtLevel(1, 2)
//      .withPeopleAtLevel(2, 20)
//      .withPeopleAtLevel(3, 100)
//      .withPeopleAtLevel(4, 500)
//      .withPeopleAtLevel(5, 5000)
//      .withPeopleAtLevel(6, 10000)
//      .withPeopleAtLevel(7, 50000)
//      .withPeopleAtLevel(8, 1934378)
//      .distribute(Contiguous)

    //    val neoDb = NeoDB("http://localhost:7474/db/data")
    //	  val basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable"
    val neoDb = NeoDBBatchInserter(basePath + "/NEO4J_DATA/apiary_2m_l7")

    builder buildWith neoDb
  }
}
