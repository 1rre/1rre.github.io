package projects

import org.scalajs.dom.{html, raw}
import util.Random

import es.tmoor.scalayout.element.canvas.Context

case class Rover(canvas: html.Canvas) extends Project("EEE Rover", 25d) {
  final val BackgroundColour = "#E5E5E5"
  final val CarColour = "#E04747"
  final val TyreColour = "#1C1F22"
  final val GrassColour = "#1B671A"
  final val RoadColour = "#323232"
  final val RoadLineColour = "#FDFFE9"
  final val HeadlampsColour = "#fff47E7F"
  final val RoadWidth = 30
  final val RoadRadius = Width / 3d
  final val AnglePerTick = 2 * math.Pi / (refreshRate * 19.2)

  val layoutContext = new Context(context)

  object Car {
    final val CarW = 15
    final val CarL = 30
    final val TyreW = 2.5
    final val TyreL = 5
    var x = Width / 2d + RoadRadius - CarW/2d + 4
    def relX = x - Width/2d
    var y = Height / 2d
    def relY = y - Height/2d
    var ang = 0d
    final val EEEFont = "12px 'Overpass Mono', monospace"

    def drawHeadLamps(): Unit = {
      context.fillStyle = HeadlampsColour
      context.beginPath()
      context.moveTo(CarW/3d, CarL/2)
      context.lineTo(CarW*1/2d, CarL*17/12d)
      context.lineTo(-CarW*2/3d, CarL*15/12d)
      context.closePath()
      context.fill()
      context.beginPath()
      context.moveTo(-CarW/3d, CarL/2)
      context.lineTo(-CarW, CarL*7/6d)
      context.lineTo(0, CarL*4/3d)
      context.closePath()
      context.fill()
    }

    def drawEEE(): Unit = {
      context.rotate(math.Pi/2)
      context.textAlign = "center"
      context.textBaseline = "middle"
      context.font = EEEFont
      context.fillText("EEE", 0, 0)
      context.rotate(-math.Pi/2)
    }

    def draw(): Unit = {
      context.translate(x, y)
      context.rotate(ang)
      layoutContext.fill.colour = CarColour
      layoutContext.drawRoundedRectangle(-CarW/2, -CarL/2, CarW, CarL, 3)
      layoutContext.fill()
      layoutContext.fill.colour = TyreColour
      layoutContext.drawRoundedRectangle(-CarW/2 - TyreW/2, -CarL/2 + TyreL, TyreW, TyreL, 1)
      layoutContext.fill()
      layoutContext.drawRoundedRectangle(CarW/2, -CarL/2 + TyreL, TyreW, TyreL, 1)
      layoutContext.fill()
      layoutContext.drawRoundedRectangle(-CarW/2 - TyreW/2, CarL/2 - 2*TyreL, TyreW, TyreL, 1)
      layoutContext.fill()
      layoutContext.drawRoundedRectangle(CarW/2, CarL/2 - 2*TyreL, TyreW, TyreL, 1)
      layoutContext.fill()
      drawEEE()
      drawHeadLamps()
      context.rotate(-ang)
      context.translate(-x, -y)
      x -= math.sin(ang)
      y += math.cos(ang)
      val at2 = math.atan2(relX, relY)
      ang += AnglePerTick
      ang %= 2*math.Pi
    }
  }

  def render(): Unit = {
    context.fillStyle = GrassColour
    context.fillRect(0, 0, Width, Height)
    context.strokeStyle = RoadColour
    context.lineWidth = RoadWidth
    context.beginPath
    context.arc(Width/2, Height/2, RoadRadius, 0, 2 * math.Pi)
    context.stroke()
    context.arc(Width/2, Height/2, RoadRadius, 0, 2 * math.Pi)
    context.strokeStyle = RoadLineColour
    context.lineWidth = 4
    context.setLineDash(scalajs.js.Array(16d, 8d))
    context.stroke()
    context.setLineDash(scalajs.js.Array())
    Car.draw()
  }

  render()
}