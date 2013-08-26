package utils.generator

import utils.generator.DistributionStrategy._
import org.neo4j.unsafe.batchinsert.BatchInserters

object DB_2M_OrgPopulator extends App {
  override def main(args: Array[String]) = {
    val orgSize = 2000000
    val basePath = "/Users/dhavald/Documents/workspace/Apiary_Stable/NEO4J_DATA/apiary_2m_l8"

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
//                          .withPeopleAtLevel(2, 500)
//                          .withPeopleAtLevel(3, 250000)
//                          .withPeopleAtLevel(4, 1749495)
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
//              .withPeopleAtLevel(2, 500)
//              .withPeopleAtLevel(3, 25000)
//              .withPeopleAtLevel(4, 1250000)
//              .withPeopleAtLevel(5, 724497)

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

//            val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 50)
//              .withPeopleAtLevel(1, 3)
//              .withPeopleAtLevel(2, 150)
//              .withPeopleAtLevel(3, 7500)
//              .withPeopleAtLevel(4, 112500)
//              .withPeopleAtLevel(5, 1687500)
//              .withPeopleAtLevel(6, 192347)
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

//            val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 50)
//              .withPeopleAtLevel(1, 2)
//              .withPeopleAtLevel(2, 100)
//              .withPeopleAtLevel(3, 500)
//              .withPeopleAtLevel(4, 2500)
//              .withPeopleAtLevel(5, 125000)
//              .withPeopleAtLevel(6, 925000)
//              .withPeopleAtLevel(7, 946898)
//              .distribute(Contiguous)

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

    new OrgLevelBuilder(orgSize, 8, Contiguous) {
      val neo4j = BatchInserters.inserter(basePath + level)
      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 500)
        .withPeopleAtLevel(1, 2)
        .withPeopleAtLevel(2, 50)
        .withPeopleAtLevel(3, 2500)
        .withPeopleAtLevel(4, 125000)
        .withPeopleAtLevel(5, 400000)
        .withPeopleAtLevel(6, 600000)
        .withPeopleAtLevel(7, 800000)
        .withPeopleAtLevel(8, 72448)
    }
  }
}
