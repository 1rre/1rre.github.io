package es.tmoor.website

import es.tmoor.scanvas.Template
import es.tmoor.scanvas.rendering.Colour
import es.tmoor.scanvas.BoundingBox

object Rover extends Project("EEE Rover", "rover-box", 50d) {
  val colour = Colour(0x1b671a)
  object Road extends SubTemplate {
    final val roadColour = Colour(0x323232)
    final val roadWidth = bounds._3 / 6d
    final val linesColour = Colour(0xfdffe9)
    final val linesWidth = bounds._3 / 48d
    final val dash = (bounds._3 / 10, bounds._3 / 20)
    def relativeBounds: BoundingBox.BoundingBox = (1 / 6d, 1 / 6d, 2 / 3d, 2 / 3d)
    def children: Seq[Template] = Nil
    def draw(): Unit = {
      context.Draw.thickness = roadWidth
      context.Draw.colour = roadColour
      context.Draw.circle(bounds)
      context.Draw.thickness = linesWidth
      context.Draw.colour = linesColour
      context.Draw.dash = dash
      context.Draw.circle(bounds)
      context.Draw.dash = Nil
    }
  }
  object Car extends SubTemplate {
    final val colour = Colour(0xe04747)
    final val radius = bounds._4 / 5
    var pos = 0d
    final val posIncrement = 0.02
    def offsetX = Road.bounds._3 / 2 * math.cos(pos)
    def offsetY = Road.bounds._3 / 2 * math.sin(pos)
    final val carLength = 1 / 7d
    final val carWidth = 1 / 14d
    final val tyreColour = Colour(0x1C1F22)
    final val tyreLength = width / 6d
    final val tyreWidth = height / 6d
    final val t1x = bounds._1 + tyreLength
    final val t1y = bounds._2 - tyreWidth/2
    final val tyre1Bounds =
      (t1x, t1y, tyreLength, tyreWidth)
    final val tyre2Bounds =
      (t1x, -t1y - tyreWidth, tyreLength, tyreWidth)
    final val tyre3Bounds =
      (-t1x - tyreLength, t1y, tyreLength, tyreWidth)
    final val tyre4Bounds =
      (-t1x - tyreLength, -t1y - tyreWidth, tyreLength, tyreWidth)
    final val tyreRadius = radius / 6
    def relativeBounds: BoundingBox.BoundingBox =
      (-carLength/2, -carWidth/2, carLength, carWidth)
    def children: Seq[Template] = Nil
    def draw(): Unit = {
      context.withOffset(-parent.width/2, -parent.height/2) {
        context.withRotation(pos) {
          context.withOffset(0, Road.bounds._3 / 2) {
            context.Fill.colour = colour
            context.Fill.roundedRect(radius, bounds)
            context.Fill.colour = tyreColour
            context.Fill.roundedRect(tyreRadius, tyre1Bounds)
            context.Fill.roundedRect(tyreRadius, tyre2Bounds)
            context.Fill.roundedRect(tyreRadius, tyre3Bounds)
            context.Fill.roundedRect(tyreRadius, tyre4Bounds)
          }
        }
      }
      pos += posIncrement
    }
  }
  def children: Seq[Template] = Seq(Road, Car)
  def draw(): Unit = {
    context.Fill.colour = colour
    context.Fill.regularPoly(4, bounds)
  }
}
