package utils.generator

case class OrganizationDef (val namesPool: List[String], val withPersonManagingMaxOf: Int = 1, val directlyReportingToMax: Int = 1, val indirectlyReportingToMax: Int = 0) {
  require(withPersonManagingMaxOf >= 0, "For an Organisation, a person manages, at max 0..* person")
  require(directlyReportingToMax >= 0, "For an Organisation, a person can report to max  0..* person")

  var from = 0
  private val peopleLevels = scala.collection.mutable.Map[Int, List[String]]()

  def peopleWithLevels = peopleLevels.toMap withDefaultValue Nil

  def totalPeople = peopleLevels.values.foldLeft(0)(_ + _.length)

  def withPeopleAtLevel(level: Int, howMany: Int) = {
    require(level > 0, "Level needs to be greater than 0")
    val people = namesPool.slice(from, from + howMany)
    from = from + howMany
    peopleLevels += (level -> people)
    info("At Level %d (%d people) = %s\n", level, people.size, people)
    this
  }

  override def toString = peopleWithLevels.map {
    case (level, people) => "Level %d, People = %d".format(level, people.size)
  }.mkString("\n")
}