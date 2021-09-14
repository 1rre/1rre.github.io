package projects
import org.scalajs.dom.{html, raw}
import scala.util.Random

case class Compiler(textDiv: html.Div) extends Project(100d) {
  final val NRows = 13
  final val NCols = 30
  val lines = Array.tabulate(NRows)(_ => Vector.fill(NCols)(' '))
  def render(): Unit = {
    val _lines = lines.clone()
    for (i <- lines.indices.tail) {
      lines(i-1) = _lines(i)
    }
    val nChars = Random.between(0,NCols+1)
    val nSpaces = NCols - nChars
    val chars = Random.alphanumeric.take(nChars).toVector
    lines(NRows-1) = chars ++ Vector.fill(nSpaces)(' ')
    val html = lines.map(_.mkString).mkString("<br>\n")
    textDiv.innerHTML = html
  }
}
