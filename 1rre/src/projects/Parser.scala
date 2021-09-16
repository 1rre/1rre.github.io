package projects

import org.scalajs.dom.{html, raw}
import util.Random

case class Parser(canvas: html.Canvas) extends Project("Parser", 100d) {
  def randomNumber = Random.nextInt(10).toString.head
  def randomOp = "+-*/" (Random.nextInt(4))
  type Value = Sum | BracketedSum | Char
  def randomSum: Value =
    if (Random.nextBoolean) new Sum(randomValue, randomOp, randomValue)
    else new BracketedSum(randomValue, randomOp, randomValue)
  def randomValue: Value =
    if (Random.nextBoolean()) randomNumber
    else randomSum
  class Sum(l: Value, o: Char, r: Value) {
    override def toString(): String = s"$l$o$r"
  }
  class BracketedSum(l: Value, o: Char, r: Value) extends Sum(l, o, r) {
    override def toString(): String = s"($l$o$r)"
  }
  lazy val sumState: String = {
    val rs = randomSum.toString
    if (rs.length > 50) rs
    else sumState
  }

  final val BackgroundColour = "#F1E297"
  final val LineColour = "#000000"
  final val TextColour = "#7D7D7D"
  final val TextFont = "72px 'Overpass Mono', monospace"
  final val HLine1Y = Height / 3
  var it = 0
  def text = {
    val takeFrom = sumState.last +: '+' +: sumState ++: '+' +: sumState.take(4)
    takeFrom.slice(it, it+5)
  }
  final val CentreBox0 = (-Width/6d, HLine1Y + Height/6d + 5)
  final val CentreBox1 = (Width/6d, HLine1Y + Height/6d + 5)
  final val CentreBox2 = (Width*3/6d, HLine1Y + Height/6d + 5)
  final val CentreBox3 = (Width*5/6d, HLine1Y + Height/6d + 5)
  final val CentreBox4 = (Width*7/6d, HLine1Y + Height/6d + 5)
  final val InitShift = Width/3d
  final val InitDelay = Width/9d
  var shiftX = 0d
  var delay = InitDelay
  final val ShiftPerFrame = 3d
  var shiftMultiplier = 1
  def drawText(): Unit = {
    context.fillStyle = TextColour
    context.textAlign = "center"
    context.textBaseline = "middle"
    context.font = TextFont
    val txt = text
    context.translate(shiftX,0)
    context.fillText(txt(0).toString, CentreBox0._1, CentreBox0._2)
    context.fillText(txt(1).toString, CentreBox1._1, CentreBox1._2)
    context.fillText(txt(2).toString, CentreBox2._1, CentreBox2._2)
    context.fillText(txt(3).toString, CentreBox3._1, CentreBox3._2)
    context.fillText(txt(4).toString, CentreBox4._1, CentreBox4._2)
    context.translate(-shiftX,0)
    if (shiftX * shiftMultiplier <= 0) {
      if (delay > 0) {
        shiftX = 0
        delay -= 1
      } else {
        if (Random.nextDouble < 0.8) {
          it += 1
          it %= (sumState.size+1)
          shiftMultiplier = 1
        } else {
          it -= 1
          if (it < 0) it = sumState.size
          shiftMultiplier = -1
        }
        shiftX = InitShift * shiftMultiplier
        delay = InitDelay
      }
    } else shiftX -= shiftMultiplier * ShiftPerFrame
  }
  def drawBoxes(): Unit = {
    context.strokeStyle = LineColour
    context.lineWidth = 2
    context.strokeRect(-1, HLine1Y, Width / 3d, Height / 3d)
    context.lineWidth = 4
    context.strokeRect(
      Width / 3d,
      HLine1Y + 1,
      Width / 3d,
      Height / 3d - 2
    )
    context.lineWidth = 2
    context.strokeRect(Width * 2 / 3d, HLine1Y, Width / 3d, Height / 3d)
  }
  def render(): Unit = {
    context.fillStyle = BackgroundColour
    context.fillRect(0, 0, Width, Height)
    drawText()
    drawBoxes()
  }

  render()
}
