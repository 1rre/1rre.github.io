package es.tmoor.website
import es.tmoor.scanvas._
import es.tmoor.scanvas.rendering.Colour
import org.scalajs.dom.raw.CanvasGradient
import scala.util.Random
import BoundingBox.BoundingBox

object CPU extends Project("CPU", "cpu-box", 25d) {
  final val background = Colour(0x3b5b6c)

  object PCB extends SubTemplate {
    final val colour = Colour(0x556c23)
    final val relativeBounds = (0.25, 0.25, 0.5, 0.5)

    object Heatsink extends SubTemplate {
      final val colour = Colour(0xa8b0b5)
      final val radius = width * 6 / 50d

      object Label extends SubTemplate {
        final val colour = Colour(0x545e64)
        final val fontSize = height * 2 / 5d
        final val font = s"${fontSize}px 'Lexend Exa', sans-serif"
        def relativeBounds: BoundingBox = (0.2, 0.2, 0.6, 0.6)
        def children: Seq[Template] = Nil
        def draw(): Unit = {
          context.Text.colour = colour
          context.Text.font = font
          val (x, y, w, h) = bounds
          val y1 = y - fontSize / 2
          val y2 = y + fontSize / 2
          context.Text.centred("MIPS", (x, y1, w, h))
          context.Text.centred("CPU", (x, y2, w, h))
        }
      }

      def relativeBounds: BoundingBox =
        (1 / 12d, 1 / 12d, 5 / 6d, 5 / 6d)
      def children: Seq[Template] = Seq(Label)
      def draw(): Unit = {
        context.Fill.colour = colour
        context.Fill.roundedRect(radius, bounds)
      }
    }

    def children = Seq(Heatsink)
    def draw() = {
      context.Fill.colour = colour
      context.Fill.regularPoly(4, bounds)
    }
  }
  abstract class Wire extends SubTemplate {
    var pulsePosition = 0d
    final val offColour = Colour(0xbcc4c4)
    final val onColour = Colour(0x45f6ff)
    final def normalThickness = 1 / 60d
    val pulseChance = 1 - 0.5d / tick
    final val increment = tick * 0.0025
    final def pulsing = pulsePosition > 0
    final def pulsingModifier = if (pulsing) 1.5 else 1
    final def relativeThickness = normalThickness * pulsingModifier
    def thickness: Double
    def p1: (Double, Double)
    def p2: (Double, Double)
    def gradient: CanvasGradient
    def children: Seq[Template] = Nil
    def draw(): Unit = {
      if (pulsePosition > 1)
        pulsePosition = 0
      if (pulsing)
        context.Draw.colour = gradient
      else
        context.Draw.colour = offColour
      context.Draw.thickness = thickness
      context.Draw.line(p1._1, p1._2, p2._1, p2._2)
      if (pulsing || Random.nextDouble() > pulseChance)
        pulsePosition += increment
    }
  }
  case class HorizontalWire(x: Double, w: Double, y: Double) extends Wire {
    final def thickness = relativeThickness * parent.height.abs
    def relativeBounds: BoundingBox = (x, y, w, y)
    def p1: (Double, Double) = (x0, y0)
    def p2: (Double, Double) =
      (x0 + width, y0)
    def gradient: CanvasGradient = {
      context.Gradient(
        p1._1,
        p1._2,
        p2._1,
        p2._2,
        (0, offColour),
        (pulsePosition, onColour),
        (1, offColour)
      )
    }

  }
  case class VerticalWire(y: Double, h: Double, x: Double) extends Wire {
    final def thickness = relativeThickness * parent.width.abs
    def relativeBounds: BoundingBox = (x, y, x, h)
    def p1: (Double, Double) = (x0, y0)
    def p2: (Double, Double) = (x0, y0 + height)
    def gradient: CanvasGradient = {
      context.Gradient(
        p1._1,
        p1._2,
        p2._1,
        p2._2,
        (0, offColour),
        (pulsePosition, onColour),
        (1, offColour)
      )
    }

  }

  val children = Seq(
    PCB,
    HorizontalWire(0, 0.25, 0.375),
    HorizontalWire(0, 0.25, 0.5),
    HorizontalWire(0, 0.25, 0.625),
    HorizontalWire(1, -0.25, 0.375),
    HorizontalWire(1, -0.25, 0.5),
    HorizontalWire(1, -0.25, 0.625),
    VerticalWire(0, 0.25, 0.375),
    VerticalWire(0, 0.25, 0.5),
    VerticalWire(0, 0.25, 0.625),
    VerticalWire(1, -0.25, 0.375),
    VerticalWire(1, -0.25, 0.5),
    VerticalWire(1, -0.25, 0.625)
  )
  def draw() = {
    context.Fill.colour = background
    context.Fill.regularPoly(4, bounds)
  }
}
