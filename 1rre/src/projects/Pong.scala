package projects

import org.scalajs.dom.{html, raw}
import util.Random

case class Pong(canvas: html.Canvas) extends Project("Pong", 25d) {
  final val BackgroundColour = "#000000"
  final val PaddleWidth = 10
  final val PaddleLength = 60
  final val PaddleColour = "#E5E5E5"
  final val PaddleMin = 30
  final val PaddleMax = 210 - PaddleLength
  final val BounceScale = 1d/28


  abstract class Paddle {
    def x: Double
    def y: Double
    def w: Double
    def h: Double
    def update(): Unit
    def draw(): Unit = {
      update()
      context.fillStyle = PaddleColour
      context.fillRect(x, y, w, h)
    }
  }

  case class HPaddle(y: Double) extends Paddle {
    var x = (Width - PaddleLength) / 2d
    def maxMove: Double = {
      val dx = (Ball.y - y).abs
      math.max(0.5, math.min((240 - dx) / 60d, math.hypot(Ball.dx, Ball.dy)))
    }
    def update(): Unit = {
      val target = Ball.x - PaddleLength/2d
      val max = math.min(PaddleMax, x + maxMove)
      val min = math.max(PaddleMin, x - maxMove)
      x = math.min(max, math.max(min, target))
    }
    def w = PaddleLength
    def h = PaddleWidth
  }
  case class VPaddle(x: Double) extends Paddle {
    var y = (Height - PaddleLength) / 2d
    def maxMove: Double = {
      val dy = (Ball.x - x).abs
      math.max(0.5, math.min((240 - dy) / 60d, math.hypot(Ball.dx, Ball.dy)))
    }
    def update(): Unit = {
      val target = Ball.y - PaddleLength/2d
      val max = math.min(PaddleMax, y + maxMove)
      val min = math.max(PaddleMin, y - maxMove)
      y = math.min(max, math.max(min, target))
    }
    def w = PaddleWidth
    def h = PaddleLength
  }
  
  val paddleH1 = new HPaddle(5)
  val paddleH2 = new HPaddle(Width - 5 - PaddleWidth)
  val paddleV1 = new VPaddle(5)
  val paddleV2 = new VPaddle(Height - 5 - PaddleWidth)

  object Ball {
    final val Radius = 5
    final val Colour = "#E5E5E5"
    final val Speed = 100 / refreshRate
    def initialise(): Unit = {
      dx = Random.nextDouble() - 0.5
      dy = Random.nextDouble() - 0.5
      val hypot = math.hypot(dx, dy)
      dx *= Speed / hypot
      dy *= Speed / hypot
      x = Width / 2d
      y = Height / 2d
    }

    var dx = 0d
    var dy = 0d
    var x = Width / 2d
    var y = Height / 2d
    initialise()

    def bounceX(variant: Double): Unit = {
      val diff = math.pow(variant * BounceScale, 5)
      dy += diff * dy.sign * 0.1
      dx *= -1
    }
    def bounceY(variant: Double): Unit = {
      val diff = math.pow(variant * BounceScale, 5)
      dx += diff * dx.sign * 0.1
      dy *= -1
    }

    def willBounceV1: Boolean = (
      x > 5 + Ball.dx + PaddleWidth + Radius &&
        x < 5 + PaddleWidth + Radius &&
        y + Radius >= paddleV1.y &&
        y - Radius <= paddleV1.y + PaddleLength
    )
    def willBounceV2: Boolean = (
      x < 235 + Ball.dx - PaddleWidth - Radius &&
        x > 235 - PaddleWidth - Radius &&
        y + Radius >= paddleV2.y &&
        y - Radius <= paddleV2.y + PaddleLength
    )
    def willBounceH1: Boolean = (
      y > 5 + Ball.dy + PaddleWidth + Radius &&
        y < 5 + PaddleWidth + Radius &&
        x + Radius >= paddleH1.x && 
        x - Radius <= paddleH1.x + PaddleLength
    )
    def willBounceH2: Boolean = (
      y < 235 + Ball.dy - PaddleWidth - Radius &&
        y > 235 - PaddleWidth - Radius &&
        x + Radius >= paddleH2.x && 
        x - Radius <= paddleH2.x + PaddleLength
    )

    def draw(): Unit = {
      context.beginPath()
      context.fillStyle = Colour
      context.arc(x, y, Radius, 0, 2 * math.Pi)
      context.fill()
      x += dx
      y += dy
      if (willBounceV1)
        bounceX(paddleV1.x+PaddleLength/2 - Ball.x)
      if (willBounceV2)
        bounceX(paddleV2.x+PaddleLength/2 - Ball.x)
      if (willBounceH1)
        bounceY(paddleH1.y+PaddleLength/2 - Ball.y)
      if (willBounceH2)
        bounceY(paddleH2.y+PaddleLength/2 - Ball.y)
      if (x < 0 || y < 0 || x > Width || y > Height)
        initialise()
    }
  }

  def drawPaddles(): Unit = {
    paddleH1.draw()
    paddleH2.draw()
    paddleV1.draw()
    paddleV2.draw()
  }

  def drawBackground(): Unit = {
    context.fillStyle = BackgroundColour
    context.fillRect(0, 0, Width, Height)
  }

  def drawBall(): Unit = {}

  def render(): Unit = {
    drawBackground()
    drawPaddles()
    Ball.draw()
  }

  render()
}
