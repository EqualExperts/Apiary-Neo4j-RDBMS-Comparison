package utils

import java.util.Date

package object generator {
  def logToConsole(logLevel: String, text: String, xs: Any*) =
    printf("[" + new Date + "] [" + logLevel + "]: " + text + "\n", xs: _*)

  def debug : Unit = debug(_: String)
  def debug(text: String, xs: Any*) = logToConsole("DEBUG", text, xs: _*)

  def info : Unit = info(_: String)
  def info(text: String, xs: Any*) = logToConsole("INFO", text, xs: _*)

  def error : Unit = error(_: String)
  def error(text: String, xs: Any*) = logToConsole("ERROR", text, xs: _*)

  def fatal : Unit = fatal(_: String)
  def fatal(text: String, xs: Any*) = logToConsole("FATAL", text, xs: _*)

  def measure[I, O](message: String, f: I => O, arg0: I): O = {
    val startTime = System.currentTimeMillis
    info(message)
    val result = f(arg0)
    val diff = System.currentTimeMillis - startTime
    info(message + " Complete. Took %d(ms) = %.3f(secs) to execute", diff, diff.toFloat/1000 )
    result
  }
}
