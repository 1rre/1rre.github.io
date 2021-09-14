package projects
import org.scalajs.dom.{html, raw}
import scala.scalajs.js.timers._

abstract class Project(refreshRate: Double = 10d) {
  def render(): Unit
  
  setInterval(refreshRate)(render())
}