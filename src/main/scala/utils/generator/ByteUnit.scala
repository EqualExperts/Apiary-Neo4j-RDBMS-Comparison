package utils.generator

class ByteUnit private (val factor: Int, val byteUnit : ByteUnit, val notation: String) {
  val bytes: Int = if (byteUnit == null) factor else factor * byteUnit.bytes
  def < (other: ByteUnit) = bytes < other.bytes
  def > (other: ByteUnit) = bytes > other.bytes
  override def toString = notation
}

object ByteUnit {
  val B  = new ByteUnit(1,  null, "B")
  val KB = new ByteUnit(1024,  B, "KB")
  val MB = new ByteUnit(1024, KB, "MB")
  val GB = new ByteUnit(1024, MB, "GB")
}

case class MemorySize (val value: Double, val unit: ByteUnit) {
  def in(to: ByteUnit) =
    if (unit == to) this
    else if (unit < to) MemorySize(value * unit.bytes / to.bytes, to)
    else MemorySize(value * unit.bytes / to.factor, to)

  override def toString = "%.2f %s".format(value, unit)
}
