package es.tmoor.website

import es.tmoor.scanvas._
import BoundingBox._
import es.tmoor.scanvas.rendering._
import scala.util.Random

object Compiler extends Project("Compiler", "compiler-box", 100d) {
  final val splitAt = 0.15

  object StatusBar extends Template(Compiler, context) {
    final val colour = Colour(0xe5e5e5)
    def relativeBounds: BoundingBox = (0, 0, 1, splitAt)
    object Title extends Template(StatusBar, context) {
      final val colour = Colour(0x1c1f22)
      def fontSize = height / 2d
      def font = s"${fontSize}px 'Lexend Exa', sans-serif"
      def relativeBounds: BoundingBox.BoundingBox = (0.05, 0.05, 0.9, 0.9)
      def children: Seq[Template] = Nil
      def draw(): Unit = {
        context.Text.colour = colour
        context.Text.font = font
        context.Text.left("C Compiler", bounds)
      }
    }
    object Close extends Template(StatusBar, context) {
      final val colour = Colour(0xe04747)
      final val crossColour = Colour(0xE5E5E5)
      def centre = (x0 + width / 2d, y0 + height / 2d)
      def thickness = width min height / 18d
      def x1 = centre._1 + radius/2d
      def y1 = centre._2 + radius/2d
      def x2 = centre._1 - radius/2d
      def y2 = centre._2 - radius/2d
      def radius = width min height * 3 / 8d
      def relativeBounds: BoundingBox = (1 - splitAt, 0, splitAt, 1)

      def children: Seq[Template] = Nil
      def draw(): Unit = {
        context.Fill.colour = colour
        context.Fill.circle(radius, centre)
        context.Draw.colour = crossColour
        context.Draw.thickness = thickness
        context.Draw.line(x1,y1,x2,y2)
        context.Draw.line(x1,y2,x2,y1)
      }
    }
    def children: Seq[Template] = Seq(
      Title,
      Close
    )
    def draw(): Unit = {
      context.Fill.colour = colour
      context.Fill.regularPoly(4, bounds)
    }
  }
  object Terminal extends Template(Compiler, context) {
    final val colour = Colour(0x1c1f22)
    final val nRows = 16
    final val nCols = 30
    final val altColourThreshold = 0.825
    def fontSize = width / 20d
    def font = s"${fontSize}px 'Overpass Mono', monospace"
    def textColour =
      if (Random.nextDouble() > altColourThreshold) Colour(0xe04747)
      else Colour(0x007f0e)
    val lines = Array.fill(nRows)(Vector.fill(nCols)(' '), colour)
    def updateLines(): Unit = {
      for (i <- lines.indices.tail) {
        lines(i - 1) = lines(i)
      }
      val nChars = Random.between(0, nCols + 1)
      val nSpaces = nCols - nChars
      val chars = Random.alphanumeric.take(nChars).toVector
      lines(nRows - 1) = (chars ++ Vector.fill(nSpaces)(' '), textColour)
    }
    def relativeBounds: BoundingBox = (0, splitAt, 1, 1 - splitAt)
    def children: Seq[Template] = Nil
    def textOffsetX = width * 0.025
    def textOffsetY = height * 0.03
    def draw(): Unit = {
      context.Fill.colour = colour
      context.Fill.regularPoly(4, bounds)
      for (i <- lines.indices) {
        context.Text.font = font
        context.Text.colour = lines(i)._2
        val (_x, _y, _w, _) = bounds
        val x = _x + textOffsetX
        val y = _y + textOffsetY + i * fontSize
        val w = _w - textOffsetX
        val h = fontSize
        context.Text.left(lines(i)._1.mkString, (x, y, w, h))
      }
      updateLines()
    }
  }
  val children: Seq[Template] = Seq(
    StatusBar,
    Terminal
  )
  def draw(): Unit = {}
}
