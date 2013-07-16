package utils.generator

import scala.util.Random
import utils.NeoDB
import DistributionStrategy._

object Neo4J_1MOrgPopulator extends App with NamesData {
  override def main(args: Array[String]) = {
    val names = syntheticNames(1000000)
    //    val names = naturalNames(1000000)

    /**
     * case 1:
     * total people in organisation = 1000000, with Levels = 6, withPersonManagingMaxOf = 10, directlyReportingToMax = 1
     *  At Level 1 => 10
     *  At Level 2 => 100
     *  At Level 3 => 1000
     *  At Level 4 => 10000
     *  At Level 5 => 100000
     *  At Level 6 => 888890
     *  Total => 1000000
     */

    val builder = OrganizationBuilder(Random.shuffle(names), withPersonManagingMaxOf = 10)
      .withPeopleAtLevel(1, 10)
      .withPeopleAtLevel(2, 100)
      .withPeopleAtLevel(3, 1000)
      .withPeopleAtLevel(4, 10000)
      .withPeopleAtLevel(5, 100000)
      .withPeopleAtLevel(6, 888890)
      .distribute(Even)

    val neoDb = NeoDB("http://localhost:7474/db/data")
    builder buildWith neoDb

  }
}
