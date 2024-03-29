package es.tmoor.scanvas
import org.scalajs.dom.{html, raw, document}
import scala.scalajs.js.timers._
import rendering.Context
import BoundingBox._

abstract class SCanvas(web: html.Canvas, val tick: Double, width: Int, height: Int)
    extends BaseTemplate {
  def this(web: raw.Element, tick: Double, w: Int, h: Int) =
    this(web.asInstanceOf[html.Canvas], tick, w, h)
  def this(id: String, tick: Double, w: Int, h: Int) =
    this(document.getElementById(id), tick, w, h)
  
  def context = new Context(web)
  def bounds = (0d, 0d, width.toDouble, height.toDouble)
  def relativeBounds: BoundingBox = (0, 0, 1, 1)
  def init(): Unit = {
    web.width = width
    web.height = height
    render()
    setInterval(tick)(render())
  }
}