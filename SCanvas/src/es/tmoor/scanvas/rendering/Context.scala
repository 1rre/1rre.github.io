package es.tmoor.scanvas.rendering

import org.scalajs.dom.html
import org.scalajs.dom.raw.{CanvasRenderingContext2D, CanvasGradient}
import es.tmoor.scanvas.BoundingBox._
import org.scalajs.dom.raw.HTMLImageElement

class Context(private[rendering] val c2d: CanvasRenderingContext2D) {
  def this(cnv: html.Canvas) =
    this(cnv.getContext("2d").asInstanceOf[CanvasRenderingContext2D])

  type Colour = String
  def withOffset(offset: (Double, Double))(fn: => Unit) = {
    c2d.translate(-offset._1, -offset._2)
    fn
    c2d.translate(offset._1, offset._2)
  }
  def withRotation(r: Double)(fn: => Unit) = {
    c2d.rotate(r)
    fn
    c2d.rotate(-r)
  }

  def background = c2d.canvas.style.background
  def background_=(s: String) = c2d.canvas.style.background = s
  object Text {
    def colour = c2d.fillStyle
    def colour_=(c: Colour) = c2d.fillStyle = c
    def colour_=(g: CanvasGradient) = c2d.fillStyle = g
    def font = c2d.font
    def font_=(f: String) = c2d.font = f
    def centred(s: String, bounds: BoundingBox) = {
      val (x, y, w, h) = bounds
      c2d.textAlign = "center"
      c2d.textBaseline = "middle"
      c2d.fillText(s, x + w / 2, y + h / 2)
    }
    def left(s: String, bounds: BoundingBox) = {
      val (x, y, w, h) = bounds
      c2d.textAlign = "left"
      c2d.textBaseline = "middle"
      c2d.fillText(s, x, y + h / 2)
    }
  }
  object Glow {
    def size = c2d.shadowBlur
    def size_=(d: Double) = c2d.shadowBlur = d
    def colour = c2d.shadowColor
    def colour_=(c: Colour) = c2d.shadowColor = c
  }
  object Fill {
    def colour = c2d.fillStyle
    def colour_=(c: Colour) = c2d.fillStyle = c
    def colour_=(g: CanvasGradient) = c2d.fillStyle = g
    def regularPoly(sides: Int, bounds: BoundingBox): Unit = {
      c2d.beginPath()
      Trace.regularPoly(sides, bounds)
      c2d.fill()
    }
    def circle(b: BoundingBox) = {
      c2d.beginPath()
      Trace.circle(b)
      c2d.fill()
    }
    def circle(r: Double, centre: (Double, Double)) = {
      c2d.beginPath()
      Trace.circle(r, centre)
      c2d.fill()
    }
    def roundedRect(r: Double, bounds: BoundingBox): Unit = {
      c2d.beginPath()
      Trace.roundedRect(r, bounds)
      c2d.fill()
    }
  }
  object Draw {
    def colour = c2d.strokeStyle
    def colour_=(c: Colour) = c2d.strokeStyle = c
    def colour_=(g: CanvasGradient) = c2d.strokeStyle = g
    def thickness = c2d.lineWidth
    def thickness_=(d: Double) = c2d.lineWidth = d
    def dash = c2d.getLineDash()
    def dash_=(onOff: (Double, Double)) = c2d.setLineDash(scalajs.js.Array(onOff._1, onOff._2))
    def dash_=(nil: Nil.type) = c2d.setLineDash(scalajs.js.Array())
    def dash_=(array: scalajs.js.Array[Double]) = c2d.setLineDash(array)
    
    def line(x1: Double, y1: Double, x2: Double, y2: Double) = {
      c2d.beginPath()
      Trace.line(x1, y1, x2, y2)
      c2d.closePath()
      c2d.stroke()
    }
    def circle(b: BoundingBox) = {
      c2d.beginPath()
      Trace.circle(b)
      c2d.stroke()
    }
    def circle(r: Double, centre: (Double, Double)) = {
      c2d.beginPath()
      Trace.circle(r, centre)
      c2d.stroke()
    }
    def regularPoly(sides: Int, bounds: BoundingBox): Unit = {
      c2d.beginPath()
      Trace.regularPoly(sides, bounds)
      c2d.stroke()
    }
    def roundedRect(r: Double, bounds: BoundingBox): Unit = {
      c2d.beginPath()
      Trace.roundedRect(r, bounds)
      c2d.stroke()
    }
  }
  object Gradient {
    def apply(
        x1: Double,
        y1: Double,
        x2: Double,
        y2: Double,
        points: (Double, Colour)*
    ) = {
      val grad = c2d.createLinearGradient(x1, y1, x2, y2)
      for ((offset, c) <- points) grad.addColorStop(offset, c)
      grad
    }
  }
  object Trace {
    def roundedRect(r: Double, bounds: BoundingBox): Unit = {
      val (x, y, w, h) = bounds
      c2d.moveTo(x, y + r)
      c2d.arc(x + r, y + r, r, math.Pi, 3 * math.Pi / 2)
      c2d.lineTo(x + w - r, y)
      c2d.arc(x + w - r, y + r, r, 3 * math.Pi / 2, 0)
      c2d.lineTo(x + w, y + h - r)
      c2d.arc(x + w - r, y + h - r, r, 0, math.Pi / 2)
      c2d.lineTo(x + r, y + h)
      c2d.arc(x + r, y + h - r, r, math.Pi / 2, math.Pi)
      c2d.moveTo(x, y + r)
      c2d.arc(x + r, y + r, r, math.Pi, 3 * math.Pi / 2)
    }
    def circle(bounds: BoundingBox): Unit = {
      val x = bounds._1 + bounds._3 / 2
      val y = bounds._2 + bounds._4 / 2
      val r = math.min(bounds._3 / 2, bounds._4 / 2)
      c2d.arc(x, y, r, 0, 2 * math.Pi)
    }
    def circle(r: Double, centre: (Double, Double)): Unit = {
      val (x, y) = centre
      c2d.arc(x, y, r, 0, 2 * math.Pi)
    }
    def line(x1: Double, y1: Double, x2: Double, y2: Double) = {
      c2d.moveTo(x1, y1)
      c2d.lineTo(x2, y2)
    }
    def regularPoly(sides: Int, bounds: BoundingBox): Unit = {
      val (x, y, w, h) = bounds
      val anglePerSide = 2 * math.Pi / sides
      var pos = (0d, 0d)
      var maxX = 0d
      var maxY = 0d
      var minX = 0d
      var minY = 0d
      var angle = 0d
      val localPoints = for (i <- 0 to sides + 1) yield {
        pos = (pos._1 + math.cos(angle), pos._2 + math.sin(angle))
        angle += anglePerSide
        if (pos._1 > maxX) maxX = pos._1
        if (pos._2 > maxY) maxY = pos._2
        if (pos._1 < minX) minX = pos._1
        if (pos._2 < minY) minY = pos._2
        pos
      }
      val points = localPoints.map {
        case (px, py) => {
          (
            x + w * ((maxX - px) / (maxX - minX)),
            y + h * ((maxY - py) / (maxY - minY))
          )
        }
      }
      c2d.moveTo(points.head._1, points.head._2)
      points.foreach { case (nextX, nextY) =>
        c2d.lineTo(nextX, nextY)
      }
    }
  }
}
