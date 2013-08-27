package utils.generator

import DistributionStrategy._
import org.neo4j.unsafe.batchinsert.BatchInserters


object DB_3M_OrgPopulator extends App with NamesGenerator {
  override def main(args: Array[String]) = {
    val basePath = "/Users/dhavald/Documents/workspace/Apiary/NEO4J_DATA/apiary_3m_l8"
    val orgSize = 3000000

    /**
     * case 1:
     * total people in organisation = 3000000, with Levels = 3, withPersonManagingMaxOf = 1000, directlyReportingToMax = 1
     *  At Level 1 => 10
     *  At Level 2 => 10000
     *  At Level 3 => 1989990
     *  Total => 3000000
     */

//            val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 1000)
//                               .withPeopleAtLevel(1, 10)
//                               .withPeopleAtLevel(2, 10000)
//                               .withPeopleAtLevel(3, 2989990)
//                               .distribute(Contiguous)

    /**
     * case 2:
     * total people in organisation = 3000000 with levels = 4, withPersonManagingMaxOf = 500, directlyReportingToMax = 1
     * At Level 1 => 5
     * At Level 2 => 25
     * At Level 3 => 6000
     * At Level 4 => 2993970
     * Total => 3000000
     */

//            val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 500)
//                              .withPeopleAtLevel(1, 5)
//                              .withPeopleAtLevel(2, 25)
//                              .withPeopleAtLevel(3, 6000)
//                              .withPeopleAtLevel(4, 2993970)
//                              .distribute(Contiguous)

    /**
     * case 3:
     * total people in organisation = 3000000 with levels = 5, withPersonManagingMaxOf = 500, directlyReportingToMax = 1
     *
     * At Level 1 => 3
     * At Level 2 => 15
     * At Level 3 => 400
     * At Level 4 => 6000
     * At Level 5 => 2993582
     * Total => 3000000
     */

//                val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 500)
//                  .withPeopleAtLevel(1, 3)
//                  .withPeopleAtLevel(2, 15)
//                  .withPeopleAtLevel(3, 400)
//                  .withPeopleAtLevel(4, 6000)
//                  .withPeopleAtLevel(5, 2993582)

    /**
     * case 4:
     * total people in organisation = 3000000 with levels = 6, withPersonManagingMaxOf = 500, directlyReportingToMax = 1
     *
     * At Level 1 => 3
     * At Level 2 => 10
     * At Level 3 => 50
     * At Level 4 => 400
     * At Level 5 => 20000
     * At Level 6 => 2979537
     * Total => 3000000
     */

//      val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 500)
//        .withPeopleAtLevel(1, 10)
//        .withPeopleAtLevel(2, 500)
//        .withPeopleAtLevel(3, 250000)
//        .withPeopleAtLevel(4, 750000)
//        .withPeopleAtLevel(5, 1600000)
//        .withPeopleAtLevel(6, 399490)
//        .distribute(Contiguous)

    /**
     * case 5:
     * total people in organisation = 3000000 with levels = 7, withPersonManagingMaxOf = 100, directlyReportingToMax = 1
     *
     * At Level 1 => 2
     * At Level 2 => 50
     * At Level 3 => 200
     * At Level 4 => 1000
     * At Level 5 => 10000
     * At Level 6 => 100000
     * At Level 7 => 1888748
     * Total => 3000000
     */

//      val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 100)
//        .withPeopleAtLevel(1, 2)
//        .withPeopleAtLevel(2, 50)
//        .withPeopleAtLevel(3, 200)
//        .withPeopleAtLevel(4, 1000)
//        .withPeopleAtLevel(5, 10000)
//        .withPeopleAtLevel(6, 100000)
//        .withPeopleAtLevel(7, 2888748)
//        .distribute(Contiguous)

    /**
     * case 6:
     * total people in organisation = 3000000 with levels = 8, withPersonManagingMaxOf = 50, directlyReportingToMax = 1
     *
     * At Level 1 => 2
     * At Level 2 => 20
     * At Level 3 => 100
     * At Level 4 => 500
     * At Level 5 => 5000
     * At Level 6 => 10000
     * At Level 7 => 50000
     * At Level 8 => 1934378
     * Total => 3000000
     */
    new OrgLevelBuilder(orgSize, 8, Contiguous) {
      val neo4jStoreDir = basePath + level
      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 50)
        .withPeopleAtLevel(1, 2)
        .withPeopleAtLevel(2, 50)
        .withPeopleAtLevel(3, 2500)
        .withPeopleAtLevel(4, 125000)
        .withPeopleAtLevel(5, 400000)
        .withPeopleAtLevel(6, 1000000)
        .withPeopleAtLevel(7, 1400000)
        .withPeopleAtLevel(8, 72448)
    }
  }
}
