package es.tmoor.website

import es.tmoor.scanvas.Template
import es.tmoor.scanvas.rendering.Colour
import scala.util.Random

object Parser extends Project("Parser", "parser-box", 25d) {
  final val bgColour = Colour(0xf1e297)
  final val lineColour = Colour(0)
  final val lineWidth = height / 120d
  final val rectWidth = 2 * lineWidth
  final val textColour = Colour(0x7d7d7d)
  final val ops = "+-*/"
  final val fontSize = 0.3 * height
  final val heightOffset = fontSize / 17
  final val boxWidth = width / 3d
  final val boxHeight = height / 3d
  final val rect =
    (boxWidth, boxWidth + lineWidth / 2, boxWidth, boxWidth - lineWidth)
  final val font = s"${fontSize}px 'Overpass Mono', monospace"
  final val tx = Seq(
    (-boxWidth, boxHeight + heightOffset, boxWidth, boxHeight),
    (0d, boxHeight + heightOffset, boxWidth, boxHeight),
    (boxWidth, boxHeight + heightOffset, boxWidth, boxHeight),
    (2 * boxWidth, boxHeight + heightOffset, boxWidth, boxHeight)
  )
  var obCnt = 0
  final val increment = 0.0375
  def randomOp = ops(Random.nextInt(ops.size))
  final val nums = "0123456789"
  def randomNum = nums(Random.nextInt(nums.size))
  def randomCh = Random.nextDouble() match {
    case x if x > 0.66  => randomNum
    case x if x > 0.33  => randomOp
    case x if x > 0.166 => '('
    case x              => ')'
  }
  final val delayInit = 40
  var direction = 0
  var delay = delayInit
  final val shiftAmount = 1d / delayInit
  var offset = 0d
  val buffer = {
    if (Random.nextBoolean()) Array(' ', ' ', ' ', randomNum)
    else {
      obCnt += 1
      Array(' ', ' ', ' ', '(')
    }
  }
  def isValid: Boolean = {
    val ch1 = buffer(buffer.length - 2)
    val ch2 = buffer.last
    if (ch1 == ' ') true
    else if (ch1.isDigit && ch1 != '0') ch2 != '(' && (obCnt > 0 || ch2 != ')')
    else if (ch1 == '(') ch2 != ')' && (!(ops contains ch2) || ch2 == '-')
    else if (ops contains ch1)
      !(ops contains ch2) && ch2 != ')' || ch2 == '-' && ch1 != '-'
    else if (ch1 == ')') (ops contains ch2) || obCnt > 0 && ch2 == ')'
    else sys.error(s"ch1 is $ch1")
  }
  def shiftBuffer() = {
    for (i <- buffer.indices.tail) buffer(i - 1) = buffer(i)
    buffer(buffer.size - 1) = randomCh
  }
  def setNewDirection(): Unit = {
    if (isValid) {
      if (buffer.last == '(') obCnt += 1
      else if (buffer.last == ')') obCnt -= 1
      direction = 1
      offset = -1
      shiftBuffer()
    } else if (offset == -1) {
      direction = 1
      buffer(buffer.size - 1) = randomCh
    } else direction = -1
  }
  def updateDelay() = {
    if (direction == 0) setNewDirection()
    else direction = 0
    delay = delayInit
  }
  def drawText() = {
    delay -= 1
    if (delay == 0) updateDelay()
    offset += direction * shiftAmount
    context.withOffset(offset * boxWidth, 0) {
      context.Text.font = font
      context.Text.colour = textColour
      for ((char, pos) <- buffer.zip(tx))
        context.Text.centred(char.toString, pos)
    }
  }
  def children: Seq[Template] = Nil
  def draw(): Unit = {
    context.Fill.colour = bgColour
    context.Fill.regularPoly(4, bounds)
    context.Draw.colour = lineColour
    context.Draw.thickness = lineWidth
    context.Draw.line(0, height / 3d, width, height / 3d)
    context.Draw.line(0, height * 2 / 3d, width, height * 2 / 3d)
    context.Draw.thickness = rectWidth
    context.Draw.regularPoly(4, rect)
    drawText()
  }
}
