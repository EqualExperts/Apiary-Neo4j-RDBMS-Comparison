package utils.generator

import DistributionStrategy._

object DB_1K_OrgPopulator extends App {

  override def main(args: Array[String]) = {
    //	  val basePath = "D:/rnd/apiary"
    val basePath = "/Users/dhavald/Documents/workspace/Apiary-Neo4j-RDBMS-Comparison/NEO4J_DATA/apiary_1k_l"
    val orgSize = 1000

    /**
     * case 1:
     * total people in organisation = 1000, with Levels = 3, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *  At Level 1 => 40
     *  At Level 2 => 160
     *  At Level 3 => 800
     *  Total => 1000
     */
    new OrgLevelBuilder(orgSize, 3, Contiguous) {
      val neo4jStoreDir = basePath + level
      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 100)
            .withPeopleAtLevel(1, 3)
            .withPeopleAtLevel(2, 10)
            .withPeopleAtLevel(3, 987)
    }.build

    /**
     * case 2:
     * total people in organisation = 1000 with levels = 4, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     * At Level 1 => 10
     * At Level 2 => 50
     * At Level 3 => 200
     * At Level 4 => 740
     * Total => 1000
     */
    /*
    new OrgLevelBuilder(orgSize, 4, Contiguous) {
      val neo4jStoreDir = basePath + level
      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 10)
        .withPeopleAtLevel(1, 3)
        .withPeopleAtLevel(2, 10)
        .withPeopleAtLevel(3, 100)
        .withPeopleAtLevel(4, 887)
    }.build

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

    new OrgLevelBuilder(orgSize, 5, Contiguous) {
      val neo4jStoreDir = basePath + level
      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 5)
      .withPeopleAtLevel(1, 3)
      .withPeopleAtLevel(2, 10)
      .withPeopleAtLevel(3, 50)
      .withPeopleAtLevel(4, 250)
      .withPeopleAtLevel(5, 687)
    }.build

    /**
     * case 4:
     * total people in organisation = 1000 with levels = 6, withPersonManagingMaxOf = 19, directlyReportingToMax = 1
     *
     * At Level 1 => 1
     * At Level 2 => 5
     * At Level 3 => 94
     * At Level 4 => 200
     * At Level 5 => 300
     * At Level 6 => 400
     * Total => 1000
     */

    new OrgLevelBuilder(orgSize, 6, Contiguous) {
      val neo4jStoreDir = basePath + level
      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 10)
      .withPeopleAtLevel(1, 2)
      .withPeopleAtLevel(2, 10)
      .withPeopleAtLevel(3, 50)
      .withPeopleAtLevel(4, 125)
      .withPeopleAtLevel(5, 300)
      .withPeopleAtLevel(6, 513)
    }.build


    /**
     * Case 5 : (Levels = 7, Manages Limit = 5)
     * At Level 1 => 2
     * At Level 2 => 5
     * At Level 3 => 20
     * At Level 4 => 70
     * At Level 5 => 140
     * At Level 6 => 280
     * At Level 7 => 483
     * */


    new OrgLevelBuilder(orgSize, 7, Contiguous) {
      val neo4jStoreDir = basePath + level
      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 5)
      .withPeopleAtLevel(1, 2)
      .withPeopleAtLevel(2, 5)
      .withPeopleAtLevel(3, 20)
      .withPeopleAtLevel(4, 70)
      .withPeopleAtLevel(5, 140)
      .withPeopleAtLevel(6, 280)
      .withPeopleAtLevel(7, 483)
    }.build

    /**
     * Case 6 : (Levels = 8, Manages Limit = 4)
     * At Level 1 => 1
     * At Level 2 => 3
     * At Level 3 => 7
     * At Level 4 => 12
     * At Level 5 => 25
     * At Level 6 => 52
     * At Level 7 => 200
     * At Level 8 => 700
     * */


    new OrgLevelBuilder(orgSize, 8, Contiguous) {
      val neo4jStoreDir = basePath + level
      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 5)
      .withPeopleAtLevel(1, 1)
      .withPeopleAtLevel(2, 3)
      .withPeopleAtLevel(3, 7)
      .withPeopleAtLevel(4, 12)
      .withPeopleAtLevel(5, 25)
      .withPeopleAtLevel(6, 52)
      .withPeopleAtLevel(7, 200)
      .withPeopleAtLevel(8, 700)
    }.build

    */
  }
}