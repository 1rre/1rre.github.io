package es.tmoor.website

import es.tmoor.scanvas.Template
import es.tmoor.scanvas.rendering.Colour
import es.tmoor.scanvas.BoundingBox.BoundingBox
import util.Random

object Pong extends Project("Pong", "pong-box", 25d) {
  final val colour = Colour(0)
  final val padding = 1 / 48d
  final val paddleThickness = 1 / 24d
  final val paddleLength = 1 / 4d
  final val textLowerColour = Colour(0xe04747)
  final val textUpperColour = Colour(0x1a50f7)
  final val fontSize = width / 12d
  final val lineSpacing = fontSize * 0.25
  final val font = s"${fontSize}px 'Lexend Exa', sans-serif"
  object Ball extends SubTemplate {
    final val colour = Colour(0xffffff)
    final val radius = 0.02
    final val initialSpeed = 0.012
    final val maxSpeed = 0.025
    final val bounceEffect = 1 / 3d
    final val delayInit = 10 * (50 / tick).toInt
    var delay = 0
    def initialise(): Unit = {
      dx = Random.between(-1d, 1d)
      dy = Random.between(-1d, 1d)
      val hypot = math.hypot(dx, dy)
      dx *= initialSpeed / hypot
      dy *= initialSpeed / hypot
      x = 0.5
      y = 0.5
    }
    var x = 0.5
    var y = 0.5
    var dx = 0d
    var dy = 0d
    initialise()
    def bounceX(variance: Double): Unit = {
      val oldSpeed = math.hypot(dx, dy)
      val diff = -dy.sign * variance.sign * math.pow(variance.abs, bounceEffect)
      dy *= 1 + diff
      dx *= -1
      val newSpeed = math.hypot(dx, dy)
      if (newSpeed > maxSpeed) {
        val scalar = maxSpeed / newSpeed
        dx *= scalar
        dy *= scalar
      } else if (newSpeed < oldSpeed) {
        val scalar = (oldSpeed / newSpeed - 1) / 2
        dx *= 1 + scalar
        dy *= 1 + scalar
      }

    }
    def bounceY(variance: Double): Unit = {
      val oldSpeed = math.hypot(dx, dy)
      val diff = -dx.sign * variance.sign * math.pow(variance.abs, bounceEffect)
      dx *= 1 + diff
      dy *= -1
      val newSpeed = math.hypot(dx, dy)
      if (newSpeed > maxSpeed) {
        val scalar = maxSpeed / newSpeed
        dx *= scalar
        dy *= scalar
      } else if (newSpeed < oldSpeed) {
        val scalar = (oldSpeed / newSpeed - 1) / 2
        dx *= 1 + scalar
        dy *= 1 + scalar
      }
    }
    def willBounceLeft: Boolean = (
      relativeBounds._1 > Ball.dx + padding + paddleThickness &&
        relativeBounds._1 < padding + paddleThickness &&
        relativeBounds._2 + relativeBounds._4 >= railLeft.Paddle.pos - paddleLength / 2 &&
        relativeBounds._2 <= railLeft.Paddle.pos + paddleLength / 2
    )
    def willBounceRight: Boolean = (
      relativeBounds._1 + relativeBounds._3 > 1 - (padding + paddleThickness) &&
        relativeBounds._1 + relativeBounds._3 < Ball.dx + 1 - (padding + paddleThickness) &&
        relativeBounds._2 + relativeBounds._4 >= railRight.Paddle.pos - paddleLength / 2 &&
        relativeBounds._2 <= railRight.Paddle.pos + paddleLength / 2
    )
    def willBounceTop: Boolean = (
      relativeBounds._2 > Ball.dy + padding + paddleThickness &&
        relativeBounds._2 < padding + paddleThickness &&
        relativeBounds._1 + relativeBounds._3 >= railTop.Paddle.pos - paddleLength / 2 &&
        relativeBounds._1 <= railTop.Paddle.pos + paddleLength / 2
    )
    def willBounceBottom: Boolean = (
      relativeBounds._2 + relativeBounds._4 > 1 - (padding + paddleThickness) &&
        relativeBounds._2 + relativeBounds._4 < Ball.dy + 1 - (padding + paddleThickness) &&
        relativeBounds._1 + relativeBounds._3 >= railBottom.Paddle.pos - paddleLength / 2 &&
        relativeBounds._1 <= railBottom.Paddle.pos + paddleLength / 2
    )
    def relativeBounds: BoundingBox = (x - radius, y - radius, 2 * radius, 2 * radius)
    def children: Seq[Template] = Nil
    def draw(): Unit = {
    println(s"Speed: ${math.hypot(dx, dy)}")
      if (delay > 0) delay -= 1
      else {
        context.Fill.colour = colour
        context.Fill.circle(bounds)
        x += dx
        y += dy
        if (willBounceLeft)
          bounceX(railLeft.Paddle.pos - Ball.y)
        if (willBounceRight)
          bounceX(railRight.Paddle.pos - Ball.y)
        if (willBounceTop)
          bounceY(railTop.Paddle.pos - Ball.x)
        if (willBounceBottom)
          bounceY(railBottom.Paddle.pos - Ball.x)
        if (x < 0 || y < 0 || x > 1 || y > 1) {
          delay = delayInit
          initialise()
        }
      }
    }
  }
  abstract class Rail extends SubTemplate {
    final val railBuffer = 0.125
    final val railLength = 1 - 2 * railBuffer
    final val movementScale = 1.5d
    final val maxLeft = railBuffer + paddleLength / 2
    final val maxRight = 1 - maxLeft
    final val maxDist = 0.8
    def paddleBounds: BoundingBox
    object Paddle extends SubTemplate {
      final val colour = Colour(0xffffff)
      var pos = 1 / 2d
      def relativeBounds: BoundingBox =
        paddleBounds
      def children: Seq[Template] = Nil
      def draw(): Unit = {
        context.Fill.colour = colour
        context.Fill.regularPoly(4, bounds)
      }
    }
    def movePerTurn: Double
    def target: Double
    def move(): Unit = {
      val mpt = movePerTurn
      val moveToStart = math.max(maxLeft, Paddle.pos - mpt)
      val moveToEnd = math.min(maxRight, Paddle.pos + mpt)
      Paddle.pos = math.min(moveToEnd, math.max(moveToStart, target))
    }
    def children: Seq[Template] = Seq(Paddle)
    def draw(): Unit = {
      move()
    }
  }
  class HorizontalRail(x: Double) extends Rail {
    def paddleBounds: BoundingBox = (0, Paddle.pos - paddleLength / 2, 1, paddleLength)
    def relativeBounds: BoundingBox = (x, 0, paddleThickness, 1)
    def target: Double = Ball.y
    def movePerTurn: Double = {
      val dx = (Ball.x - x).abs
      val ballSpeed = math.hypot(Ball.dx, Ball.dy)
      val scaledDistance = 1 - math.pow(dx, movementScale)
      math.min(maxDist, scaledDistance) * ballSpeed
    }
  }
  class VerticalRail(y: Double) extends Rail {
    def paddleBounds: BoundingBox =
      (Paddle.pos - paddleLength / 2, 0, paddleLength, 1)
    def relativeBounds: BoundingBox = (0, y, 1, paddleThickness)
    def target: Double = Ball.x
    def movePerTurn: Double = {
      val dy = (Ball.y - y).abs
      val ballSpeed = math.hypot(Ball.dx, Ball.dy)
      val scaledDistance = 1 - math.pow(dy, movementScale)
      math.min(maxDist, scaledDistance) * ballSpeed
    }
  }
  val railLeft = new HorizontalRail(padding)
  val railRight = new HorizontalRail(1 - padding - paddleThickness)
  val railTop = new VerticalRail(padding)
  val railBottom = new VerticalRail(1 - padding - paddleThickness)
  def children: Seq[Template] = Seq(
    railLeft,
    railRight,
    railTop,
    railBottom,
    Ball
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
