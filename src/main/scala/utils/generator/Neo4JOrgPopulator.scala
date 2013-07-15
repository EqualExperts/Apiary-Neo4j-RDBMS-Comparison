package utils.generator

import scala.util.Random
import utils.{NeoDB}

object Neo4JOrgPopulator extends App {
  def syntheticNames =
    (for {
      i <- 1 to 1000
      j <- 1 to 1000
    } yield "first" + i + " last" + j).toList

  def naturalNames =  {
    val parentPath = "src" :: "main" :: "resources" :: Nil
    val firstNames = NamesLoader(parentPath, List("firstNames.txt"))
    val lastNames  = NamesLoader(parentPath, List("lastNames.txt"))

    (for {
      firstName <- firstNames
      lastName <- lastNames
    } yield "first" + firstName + " " + "last" + lastName).toList
  }

  override def main(args: Array[String]) = {


    /**
     * case 1:
     * total people in organisation = 1000, with Levels = 3, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     *  At Level 1 => 40
     *  At Level 2 => 160
     *  At Level 3 => 800
     *  Total => 1000
     */

    //    val builder = OrganizationBuilder(Random.shuffle(naturalNames), withPersonManagingMaxOf = 5)
    //                        .withPeopleAtLevel(1, 40)
    //                        .withPeopleAtLevel(2, 160)
    //                        .withPeopleAtLevel(3, 800)


    /**
     * case 2:
     * total people in organisation = 1000 with levels = 4, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
     * At Level 1 => 10
     * At Level 2 => 50
     * At Level 3 => 200
     * At Level 4 => 740
     * Total => 1000
     */

    //    val builder  = OrganizationBuilder(Random.shuffle(naturalNames), withPersonManagingMaxOf = 5)
    //                      .withPeopleAtLevel(1, 10)
    //                      .withPeopleAt0.12.3Level(2, 50)
    //                      .withPeopleAtLevel(3, 200)
    //                      .withPeopleAtLevel(4, 740)

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

    val builder = OrganizationBuilder(Random.shuffle(syntheticNames), withPersonManagingMaxOf = 5)
      .withPeopleAtLevel(1, 3)
      .withPeopleAtLevel(2, 15)
      .withPeopleAtLevel(3, 75)
      .withPeopleAtLevel(4, 300)
      .withPeopleAtLevel(5, 607)

    val neoDb = NeoDB("http://localhost:7474/db/data")
    builder buildWith neoDb

  }
}