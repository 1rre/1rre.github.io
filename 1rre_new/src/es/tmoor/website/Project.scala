package es.tmoor.website
import org.scalajs.dom.{raw, html, document}
import es.tmoor.scanvas._

abstract class Project(val title: String, canvasName: String, refreshRate: Double)
    extends SCanvas(canvasName, refreshRate, Project.Width, Project.Height)

object Project {
  final val Width = 240
  final val Height = 240
}