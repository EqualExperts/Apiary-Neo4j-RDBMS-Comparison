package utils

import java.io.{File, InputStream}

object NamesLoader {
  private def loadNamesFrom(s: Option[InputStream]) = {
    s match {
      case None => sys.error("Names.txt file not Found!! Please Check src/main/resources dir.")
      case Some(is) => {
        try {
          val s = io.Source.fromInputStream(is)
          s.getLines.toList
        } catch {
          case e: Exception =>
            println("Could not load Names list: " + e)
            throw e
        } finally {
          is.close()
        }
      }
    }
  }

  private def asFile(file: File, children: String*) = {
    children.foldLeft(file)((file, child) => new File(file, child))
  }

  private def resourceAsStream(parentPath: List[String], resourcePath: List[String]): Option[java.io.InputStream] = {
    val classesDir = new File(getClass.getResource(".").toURI)
    val projectDir = classesDir.getParentFile.getParentFile.getParentFile
    val completePath = parentPath ::: resourcePath
    val resourceFile = asFile(projectDir, completePath: _*)
    if (resourceFile != null && resourceFile.exists)
      Some(new java.io.FileInputStream(resourceFile))
    else
      None
  }

  /**
   * (List[String], List[String]) => List[String]
   *
   * Produces list of names from the specified file residing within a resource directory inside a
   * parent directory
   *
   * @param parentPath    "src" :: "main" :: "resources" :: Nil
   * @param resourcePath  "data" :: "firstNames.txt" :: Nil
   *        resourcePath  "firstNames.txt" :: Nil
   * @return
   */
  def apply(parentPath: List[String], resourcePath: List[String]) =
    loadNamesFrom(resourceAsStream(parentPath, resourcePath))
}

