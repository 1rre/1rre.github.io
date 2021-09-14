package projects
import org.scalajs.dom.{html, raw}
import util.Random

case class Compiler(canvas: html.Canvas) extends Project("Compiler", 100d) {
  final val TerminalBackground = "#1C1F22"
  final val TerminalFont = "12px 'Overpass Mono', monospace"
  final val TerminalTextX = 5
  final val TerminalTextY = StatusBarHeight + 10
  final val NRows = 16
  final val NCols = 30

  def useAltTextColour = Random.nextDouble > 0.825
  def terminalTextColour = if (useAltTextColour) "#E04747" else "#007F0E"

  val lines = Array.tabulate(NRows)(_ => (Vector.fill(NCols)(' '), TerminalBackground))
  private def updateLines(): Unit = {
    for (i <- lines.indices.tail) {
      lines(i-1) = lines(i)
    }
    val nChars = Random.between(0,NCols+1)
    val nSpaces = NCols - nChars
    val chars = Random.alphanumeric.take(nChars).toVector
    lines(NRows-1) = (chars ++ Vector.fill(nSpaces)(' '), terminalTextColour)
  }

  def render(): Unit = {
    drawStatusBar()
    context.fillStyle = TerminalBackground
    context.fillRect(0, StatusBarHeight, Width, Height)
    val text = updateLines()
    context.font = TerminalFont
    for (i <- 0 until NRows) {
      context.fillStyle = lines(i)._2
      context.fillText(lines(i)._1.mkString, TerminalTextX, TerminalTextY+12*i)
    }

  }

  render()
}
