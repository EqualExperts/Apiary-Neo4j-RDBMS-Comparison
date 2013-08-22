package utils.generator

case class TopLevel extends AllTopLevelPeopleWithMaxCount {
  def names = Map(
    "1k_l3" -> "Bejan Augustine",
    "1k_l4" -> "Alaine Barney",
    "1k_l5" -> "Albert Bishop",
    "1k_l6" -> "Barry Ash",
    "1k_l7" -> "Amit Armstrong",
    "1k_l8" -> "Bejan Broadbent",
    "10k_l3" -> "first63 last7",
    "10k_l4" -> "first3 last95",
    "10k_l5" -> "first55 last4",
    "10k_l6" -> "first34 last100",
    "10k_l7" -> "first40 last33",
    "10k_l8" -> "first20 last58",
    "100k_l3" -> "first88 last136",
    "100k_l4" -> "first185 last260",
    "100k_l5" -> "first60 last63",
    "100k_l6" -> "first184 last278",
    "100k_l7" -> "first53 last127",
    "100k_l8" -> "first287 last205",
    "1m_l3" -> "first975 last461",
    "1m_l4" -> "first507 last452",
    "1m_l5" -> "first839 last638",
    "1m_l6" -> "first247 last855",
    "1m_l7" -> "first309 last896",
    "1m_l8" -> "first968 last252",
    "2m_l3" -> "",
    "2m_l4" -> "",
    "2m_l5" -> "first186 last68",
    "2m_l6" -> "",
    "2m_l7" -> "",
    "2m_l8" -> "first410 last858",
    "3m_l3" -> "",
    "3m_l4" -> "",
    "3m_l5" -> "",
    "3m_l6" -> "",
    "3m_l7" -> "",
    "3m_l8" -> "first783 last3")


  def newNames(basePath: String, orgSizes: List[String], levels: Range) =
    runPersonWithMaxReporteesQuery(basePath, orgSizes, levels)
}
