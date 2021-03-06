package utils.generator


import org.neo4j.unsafe.batchinsert.{BatchInserters}
import DistributionStrategy._

/**
 * Note: You may need to enable and change the old value 64M of the wrapper.java.maxmemory property
 * in neo4j-wrapper.conf.
 *
 * # Maximum Java Heap Size (in MB)
 * wrapper.java.maxmemory=512
 */
object DB_100K_OrgPopulator extends App  {

  override def main(args: Array[String]) = {
    val orgSize = 100000
    val basePath = "/Users/dhavald/Documents/workspace/Apiary/NEO4J_DATA/apiary_100k_l"

    /**
     * case 1:
     * total people in organisation = 100000, with Levels = 3, withPersonManagingMaxOf = 500, directlyReportingToMax = 1
     *  At Level 1 => 10
     *  At Level 2 => 1000
     *  At Level 3 => 98990
     *  Total => 100000
     */
    new OrgLevelBuilder(orgSize, 3, Contiguous) {
      val neo4jStoreDir = basePath + level
      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 500)
        .withPeopleAtLevel(1, 10)
        .withPeopleAtLevel(2, 1000)
        .withPeopleAtLevel(3, 98990)
    }.build


    /**
      * Case 2 - Redistributed: (Levels = 4, Manages Limit = 500)
     * At Level 1 => 5
     * At Level 2 => 25
     * At Level 3 => 4000
     * At Level 4 => 95970
     * Total => 100000
     **/
//    new OrgLevelBuilder(orgSize, 4, Contiguous) {
//      val neo4j = BatchInserters.inserter(basePath + level)
//      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 500)
//        .withPeopleAtLevel(1, 5)
//        .withPeopleAtLevel(2, 25)
//        .withPeopleAtLevel(3, 4000)
//        .withPeopleAtLevel(4, 95970)
//    }.build

    /**
      * Case 3 - Redistributed: (Levels = 5, Manages Limit = 500)
     * At Level 1 => 5
     * At Level 2 => 80
     * At Level 3 => 1000
     * At Level 4 => 12000
     * At Level 5 => 86915
     * Total => 100000
     **/
//    new OrgLevelBuilder(orgSize, 5, Contiguous) {
//      val neo4j = BatchInserters.inserter(basePath + level)
//      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 500)
//        .withPeopleAtLevel(1, 5)
//        .withPeopleAtLevel(2, 80)
//        .withPeopleAtLevel(3, 1000)
//        .withPeopleAtLevel(4, 12000)
//        .withPeopleAtLevel(5, 86915)
//    }.build

    /**
     * Case 4  - Redistributed: (Levels = 6, Manages Limit = 300)
     * At Level 1 => 3
     * At Level 2 => 10
     * At Level 3 => 50
     * At Level 4 => 200
     * At Level 5 => 10000
     * At Level 6 => 89737
     * Total => 100000
     **/
//    new OrgLevelBuilder(orgSize, 6, Contiguous) {
//      val neo4j = BatchInserters.inserter(basePath + level)
//      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 300)
//        .withPeopleAtLevel(1, 3)
//        .withPeopleAtLevel(2, 10)
//        .withPeopleAtLevel(3, 50)
//        .withPeopleAtLevel(4, 200)
//        .withPeopleAtLevel(5, 10000)
//        .withPeopleAtLevel(6, 89737)
//    }.build


    /**    Case 5  - Redistributed: (Levels = 7, Manages Limit = 100)
      * val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 100)
      .withPeopleAtLevel(1, 3)
      .withPeopleAtLevel(2, 23)
      .withPeopleAtLevel(3, 100)
      .withPeopleAtLevel(4, 800)
      .withPeopleAtLevel(5, 3000)
      .withPeopleAtLevel(6, 10000)
      .withPeopleAtLevel(7, 86074)
			.distribute(Contiguous)
      **/

//    new OrgLevelBuilder(orgSize, 7, Contiguous) {
//      val neo4j = BatchInserters.inserter(basePath + level)
//      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 100)
//        .withPeopleAtLevel(1, 3)
//        .withPeopleAtLevel(2, 23)
//        .withPeopleAtLevel(3, 100)
//        .withPeopleAtLevel(4, 800)
//        .withPeopleAtLevel(5, 3000)
//        .withPeopleAtLevel(6, 10000)
//        .withPeopleAtLevel(7, 86074)
//    }.build

    /*
         * Case 6   - Redistributed: (Levels = 8, Manages Limit = 50)
         * At Level 1 => 2
         * At Level 2 => 20
         * At Level 3 => 100
         * At Level 4 => 500
         * At Level 5 => 1500
         * At Level 6 => 7500
         * At Level 7 => 15000
         * At Level 8 => 75378
         *  Total => 100000
         */
//    new OrgLevelBuilder(orgSize, 7, Contiguous) {
//      val neo4j = BatchInserters.inserter(basePath + level)
//      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 50)
//        .withPeopleAtLevel(1, 2)
//        .withPeopleAtLevel(2, 20)
//        .withPeopleAtLevel(3, 100)
//        .withPeopleAtLevel(4, 500)
//        .withPeopleAtLevel(5, 1500)
//        .withPeopleAtLevel(6, 7500)
//        .withPeopleAtLevel(7, 15000)
//        .withPeopleAtLevel(8, 75378)
//    }.build

  }
}
