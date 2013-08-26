package utils.generator

/**
 * i/ps =>
 * 1) total people - total
 * 2) indirectRelationShips -idr  n (max)
 * 3) directRelationships:  -dr n (max)
 * 4) levelsOfDirectRelationships: -levels (valid values: 2 to 10)
 */

object DistributionStrategy extends Enumeration {
  type DistributionStrategy = Value
  val Contiguous, Even = Value
}

import DistributionStrategy._
class OrganizationBuilder (val orgDef : OrganizationDef, val distributionStrategy: DistributionStrategy = DistributionStrategy.Contiguous)

  extends Builder {

  val peopleWithLevels = orgDef.peopleWithLevels

  private def showErrorMessage(levels: List[Int]) = {
    error("Cannot distribute people properly in the hierarchy! Options: ")
    error("1) Try increasing the value of personManagingMax above %d", orgDef.withPersonManagingMaxOf)
    error("2) Alternatively, Lessen the people at ")
    levels match {
      case level :: Nil => error("=> level %d", level)
      case _ => error("=> levels %s", levels)
    }
    sys.error("Could Not Build Organization!")
  }

  private def isManageable(upperLevel: Int, lowerLevel: Int) = {
    val totalUpper = peopleWithLevels(upperLevel).length.toDouble
    val totalLower = peopleWithLevels(lowerLevel).length.toDouble
    (totalLower /  orgDef.withPersonManagingMaxOf.toDouble) <= totalUpper
  }

  private def illFormedLevels =
    orgDef.peopleWithLevels.keys.filter { level =>
      !isManageable(level, level + 1)
    }.map {
      1 +
    }.toList

  override def build : Unit = {
    info("Total in Org = %d people\n", orgDef.totalPeople)
    illFormedLevels match {
      case Nil    => info("Building Organization...")
      case levels => showErrorMessage(levels)
    }
  }
}