package es.tmoor.scanvas.rendering

import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D
import es.tmoor.scanvas.BoundingBox._

class Context(c2d: CanvasRenderingContext2D) {
  def this(cnv: html.Canvas) =
    this(cnv.getContext("2d").asInstanceOf[CanvasRenderingContext2D])
  def background = c2d.canvas.style.background
  def background_=(s: String) = c2d.canvas.style.background = s
  object Text {
    def colour = c2d.fillStyle
    def colour_=(s: String) = c2d.fillStyle = s
    def font = c2d.font
    def font_=(f: String) = c2d.font = f
    def centred(s: String, bounds: BoundingBox) = {
      val (x,y,w,h) = bounds
      c2d.textAlign = "center"
      c2d.textBaseline = "middle"
      c2d.fillText(s, x+w/2, y+h/2)
    }
  }
  object Fill {
    def colour = c2d.fillStyle
    def colour_=(s: String) = c2d.fillStyle = s
    def regularPoly(sides: Int, bounds: BoundingBox): Unit = {
      c2d.beginPath()
      Draw.regularPoly(sides, bounds)
      c2d.fill()
    }
    def roundedRect(r: Double, bounds: BoundingBox): Unit = {
      c2d.beginPath()
      Draw.roundedRect(r, bounds)
      c2d.fill()
    }
  }
  object Draw {
    def roundedRect(r: Double, bounds: BoundingBox): Unit = {
      val (x,y,w,h) = bounds
      c2d.beginPath()
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
      c2d.beginPath()
      c2d.moveTo(points.head._1, points.head._2)
      points.foreach { case (nextX, nextY) =>
        c2d.lineTo(nextX, nextY)
      }
    }
  }
}