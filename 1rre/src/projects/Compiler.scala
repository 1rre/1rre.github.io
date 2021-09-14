package projects
import org.scalajs.dom.{html, raw}
import util.Random

case class Compiler(canvas: html.Canvas) extends Project("C Compiler", 100d) {
  final val StatusBarHeight = Height * 0.15
  final val StatusBarColour = "#E5E5E5"
  final val TitleColour = "#1C1F22"
  final val TitleX = 5
  final val TitleY = StatusBarHeight / 2
  final val TitleFont = "16px 'Lexend Exa', sans-serif"
  final val CloseColour = "#E04747"
  final val CloseRadius = StatusBarHeight * 3 / 8d
  final val CloseX = Width - StatusBarHeight / 2
  final val CloseY = StatusBarHeight / 2d
  final val CrossColour = "#E5E5E5"
  final val CrossLine1X1 = CloseX - CloseRadius / 2d
  final val CrossLine1Y1 = CloseY - CloseRadius / 2d
  final val CrossLine1X2 = CloseX + CloseRadius / 2d
  final val CrossLine1Y2 = CloseY + CloseRadius / 2d
  final val CrossLine2X1 = CloseX - CloseRadius / 2d
  final val CrossLine2Y1 = CloseY + CloseRadius / 2d
  final val CrossLine2X2 = CloseX + CloseRadius / 2d
  final val CrossLine2Y2 = CloseY - CloseRadius / 2d

  def drawStatusBar(): Unit = {
    context.beginPath()
    context.fillStyle = StatusBarColour
    context.fillRect(0, 0, Width, StatusBarHeight)
    context.fillStyle = TitleColour
    context.font = TitleFont
    context.textBaseline = "middle"
    context.fillText("C Compiler", TitleX, TitleY)
    context.fillStyle = CloseColour
    context.arc(CloseX, CloseY, CloseRadius, 0, 2 * math.Pi)
    context.fill()
    context.strokeStyle = CrossColour
    context.lineWidth = 2
    context.beginPath()
    context.moveTo(CrossLine1X1, CrossLine1Y1)
    context.lineTo(CrossLine1X2, CrossLine1Y2)
    context.moveTo(CrossLine2X1, CrossLine2Y1)
    context.lineTo(CrossLine2X2, CrossLine2Y2)
    context.stroke()
  }
  final val TerminalBackground = "#1C1F22"
  final val TerminalFont = "12px 'Overpass Mono', monospace"
  final val TerminalTextX = 5
  final val TerminalTextY = StatusBarHeight + 10
  final val NRows = 16
  final val NCols = 30

  def useAltTextColour = Random.nextDouble > 0.825
  def terminalTextColour = if (useAltTextColour) "#E04747" else "#007F0E"

  val lines =
    Array.tabulate(NRows)(_ => (Vector.fill(NCols)(' '), TerminalBackground))
  private def updateLines(): Unit = {
    for (i <- lines.indices.tail) {
      lines(i - 1) = lines(i)
    }
    val nChars = Random.between(0, NCols + 1)
    val nSpaces = NCols - nChars
    val chars = Random.alphanumeric.take(nChars).toVector
    lines(NRows - 1) = (chars ++ Vector.fill(nSpaces)(' '), terminalTextColour)
  }

  def render(): Unit = {
    drawStatusBar()
    context.fillStyle = TerminalBackground
    context.fillRect(0, StatusBarHeight, Width, Height)
    val text = updateLines()
    context.font = TerminalFont
    for (i <- 0 until NRows) {
      context.fillStyle = lines(i)._2
      context.fillText(
        lines(i)._1.mkString,
        TerminalTextX,
        TerminalTextY + 12 * i
      )
    }

  }

  render()
}
