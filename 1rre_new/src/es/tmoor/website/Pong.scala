package es.tmoor.website

import es.tmoor.scanvas.Template
import es.tmoor.scanvas.rendering.Colour
import es.tmoor.scanvas.BoundingBox

object Pong extends Project("Pong", "pong-box", 50d) {
  final val colour = Colour(0)
  final val padding = 1 / 48d
  final val paddleThickness = 1 / 24d
  final val paddleLength = 1 / 4d
  final val textLowerColour = Colour(0xe04747)
  final val textUpperColour = Colour(0x1a50f7)
  final val fontSize = width / 12d
  final val lineSpacing = fontSize * 0.25
  final val font = s"${fontSize}px 'Lexend Exa', sans-serif"
  class HorizontalRail(x: Double) extends SubTemplate {
    object Paddle extends SubTemplate {
      final val colour = Colour(0xffffff)
      var pos = 1 / 2d
      def relativeBounds: BoundingBox.BoundingBox =
        (0, pos - paddleLength / 2, 1, paddleLength)
      def children: Seq[Template] = Nil
      def draw(): Unit = {
        println(s"Paddle at $x0, $y0 to ${x0 + width}, ${y0 + height}")
        context.Fill.colour = colour
        context.Fill.regularPoly(4, bounds)
      }
    }
    def relativeBounds: BoundingBox.BoundingBox = (x, 0, paddleThickness, 1)
    def children: Seq[Template] = Seq(Paddle)
    def draw(): Unit = {}
  }
  class VerticalRail(y: Double) extends SubTemplate {
    object Paddle extends SubTemplate {
      final val colour = Colour(0xffffff)
      var pos = 1 / 2d
      def relativeBounds: BoundingBox.BoundingBox =
        (pos - paddleLength / 2, 0, paddleLength, 1)
      def children: Seq[Template] = Nil
      def draw(): Unit = {
        println(s"Paddle at $x0, $y0 to ${x0 + width}, ${y0 + height}")
        context.Fill.colour = colour
        context.Fill.regularPoly(4, bounds)
      }
    }
    def relativeBounds: BoundingBox.BoundingBox = (0, y, 1, paddleThickness)
    def children: Seq[Template] = Seq(Paddle)
    def draw(): Unit = {}
  }
  def children: Seq[Template] = Seq(
    new HorizontalRail(padding),
    new HorizontalRail(1 - padding - paddleThickness),
    new VerticalRail(padding),
    new VerticalRail(1 - padding - paddleThickness)
  )
  def draw(): Unit = {
    context.Fill.colour = colour
    context.Fill.regularPoly(4, bounds)
    val (x, y, w, h) = bounds
    val my = y + h / 2
    context.Text.font = font
    context.Text.colour = textUpperColour
    context.Text.centred(
      "DISTRIBUTED",
      (0, my - fontSize - lineSpacing, width, fontSize)
    )
    context.Text.colour = textLowerColour
    context.Text.centred("PONG", (0, my + lineSpacing, width, fontSize))
  }
}
