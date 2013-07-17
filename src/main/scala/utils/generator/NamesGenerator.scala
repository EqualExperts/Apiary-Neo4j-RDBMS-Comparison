package utils.generator

import scala.util.Random

trait NamesGenerator {
  private def sqrt(x : Int) =  Math.ceil(Math.sqrt(x)).toInt

  def syntheticNames(howMany: Int) = {
    val total = sqrt(howMany)
    val names = for {
      i <- 1 to total
      j <- 1 to total
    } yield "first" + i + " last" + j
    Random.shuffle(names.toList)
  }

  def naturalNames(howMany: Int) = {
    val total = sqrt(howMany)
    val parentPath = "src" :: "main" :: "resources" :: Nil
    val firstNames = NamesLoader(parentPath, List("firstNames.txt")).take(total)
    val lastNames  = NamesLoader(parentPath, List("lastNames.txt")).take(total)

    val names = for {
      firstName <- firstNames
      lastName <- lastNames
    } yield firstName + " " + lastName
    Random.shuffle(names.toList)
  }
}
