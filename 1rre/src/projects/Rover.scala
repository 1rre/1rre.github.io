package projects

import org.scalajs.dom.{html, raw}
import util.Random

import es.tmoor.scalayout.element.canvas.Context

case class Rover(canvas: html.Canvas) extends Project("EEE Rover", 25d) {
  final val BackgroundColour = "#E5E5E5"
  final val CarColour = "#E04747"
  final val TyreColour = "#1C1F22"


  val layoutContext = new Context(context)

  object Car {
    final val CarW = 15
    final val CarL = 30
    final val TyreW = 2.5
    final val TyreL = 5
    var x = 160d
    def relX = x - Width/2d
    var y = 120d
    def relY = y - Height/2d
    var ang = 0d
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
      context.rotate(-ang)
      context.translate(-x, -y)
      x -= math.sin(ang)
      y += math.cos(ang)
      val at2 = math.atan2(relX, relY)
      ang += 0.02
      ang %= 2*math.Pi
    }
  }

  def render(): Unit = {
    context.fillStyle = BackgroundColour
    context.fillRect(0, 0, Width, Height)
    Car.draw()
  }

  render()
}