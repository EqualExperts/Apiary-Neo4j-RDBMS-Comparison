package utils.generator

import ByteUnit._
trait MemoryStatsReporter {
  private val runtime = Runtime.getRuntime
  val processors = runtime.availableProcessors

  def memStats = {
    val totalMemory = MemorySize(runtime.totalMemory, B)
    val freeMemory = MemorySize(runtime.freeMemory, B)
    val maxMemory = MemorySize(runtime.maxMemory, B)

    val report =
      """
       |MEMORY SETTINGS:
       |Total Memory (currently in JVM) = %s =~ %s =~ %s
       |Free Memory  (currently in JVM) = %s =~ %s =~ %s
       |Max Memory   (available to JVM) = %s =~ %s =~ %s
      """.format(totalMemory, totalMemory in MB, totalMemory in GB,
                 freeMemory, freeMemory in MB, freeMemory in GB,
                 maxMemory, maxMemory in MB, maxMemory in GB)

    info(report)
    (totalMemory, maxMemory, freeMemory)
  }
}
