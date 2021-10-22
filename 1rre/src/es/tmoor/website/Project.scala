package es.tmoor.website
import org.scalajs.dom.{raw, html, document}
import es.tmoor.scanvas._

abstract class Project(val title: String, canvasName: String, tick: Double)
    extends SCanvas(canvasName, tick, Project.Width, Project.Height)

object Project {
  final val Width = 640
  final val Height = 640
}