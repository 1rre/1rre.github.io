package es.tmoor.website
import es.tmoor.scanvas._
import es.tmoor.scanvas.rendering.Colour

object CPU extends Project("CPU", "cpu-box", 500d) {
  final val background = Colour(0x3b5b6c)
  def children = Seq(PCB)
  def draw() = {
    context.background = background
  }
}
object PCB extends Template(CPU, CPU.context) {
  final val colour = Colour(0x556c23)
  final val relativeBounds = (0.25, 0.25, 0.5, 0.5)
  def children = Seq(Heatsink)
  def draw() = {
    context.Fill.colour = colour
    context.Fill.regularPoly(4, bounds)
  }
}
object Heatsink extends Template(PCB, PCB.context) {
  final val colour = Colour(0xa8b0b5)
  final val radius = bounds._3 * 6 / 50d
  def relativeBounds: BoundingBox.BoundingBox =
    (1 / 12d, 1 / 12d, 5 / 6d, 5 / 6d)
  def children: Seq[Template] = Seq(Label)
  def draw(): Unit = {
    context.Fill.colour = colour
    context.Fill.roundedRect(radius, bounds)
  }
}
object Label extends Template(Heatsink, Heatsink.context) {
  final val colour = Colour(0x545e64)
  final val fontSize = bounds._4 * 2 / 5d
  final val font = s"${fontSize}px 'Lexend Exa', sans-serif"
  def relativeBounds: BoundingBox.BoundingBox = (0.2, 0.2, 0.6, 0.6)
  def children: Seq[Template] = Nil
  def draw(): Unit = {
    context.Text.colour = colour
    context.Text.font = font
    val (x,y,w,h) = bounds
    val y1 = y-fontSize/2
    val y2 = y+fontSize/2
    context.Text.centred("MIPS", (x,y1,w,h))
    context.Text.centred("CPU", (x,y2,w,h))
  }
}
