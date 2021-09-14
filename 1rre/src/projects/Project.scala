package projects
import org.scalajs.dom.{html, raw}
import scala.scalajs.js.timers._

abstract class Project(title: String, val refreshRate: Double = 10d) {
  final val Width: Int = 240
  final val Height: Int = 240

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
