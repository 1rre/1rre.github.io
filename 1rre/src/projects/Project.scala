package projects
import org.scalajs.dom.{html, raw}
import scala.scalajs.js.timers._

abstract class Project(title: String, val refreshRate: Double = 10d) {
  final val Width: Int = 240
  final val Height: Int = 240

  final val StatusBarHeight = Height * 0.15
  final val StatusBarColour = "#E5E5E5"
  final val TitleColour = "#1C1F22"
  final val TitleX = 5
  final val TitleY = StatusBarHeight/2
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
    context.fillText(title, TitleX, TitleY)
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

  def render(): Unit
  
  val canvas: html.Canvas
  
  final val context = (
    canvas.getContext("2d").asInstanceOf[raw.CanvasRenderingContext2D]
  )

  canvas.width = Width
  canvas.height = Height

  setInterval(refreshRate) {
    render()
  }
}
