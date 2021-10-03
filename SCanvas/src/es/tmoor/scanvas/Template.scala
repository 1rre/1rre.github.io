package es.tmoor.scanvas
import rendering.Context
import BoundingBox._

abstract class BaseTemplate {
  def relativeBounds: BoundingBox
  def bounds: BoundingBox
  def tick: Double
  def children: Seq[Template]
  def draw(): Unit
  final def render(): Unit = {
    draw()
    children.foreach(_.render())
  }
}
abstract class Template(val parent: BaseTemplate, val context: Context) extends BaseTemplate {
  def bounds: BoundingBox = {
    val (px,py,pw,ph) = parent.bounds
    val (x,y,w,h) = relativeBounds
    (px+x*pw, py+y*ph, w*pw, h*ph)
  }
  def tick = parent.tick
}