package es.tmoor.scanvas
import rendering.Context
import BoundingBox._

abstract class Template(parent: BoundingBox, val context: Context) {
  def this(parent: Template, context: Context) = this(parent.bounds, context)
  def relativeBounds: BoundingBox
  def bounds = {
    val (px,py,pw,ph) = parent
    val (x,y,w,h) = relativeBounds
    (px+x*pw, py+y*ph, w*pw, h*ph)
  }
  def children: Seq[Template]
  def draw(): Unit
  final def render(): Unit = {
    draw()
    children.foreach(_.render())
  }
}