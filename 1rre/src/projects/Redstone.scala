package projects
import org.scalajs.dom.{html, raw, document}
import util.Random

case class Redstone(canvas: html.Canvas) extends Project("Minecraft", 250d) {
  final val TorchStemColour = "#E0B877"
  final val TorchX = Width*9/20d
  final val TorchY = Height*2/5d
  final val TorchW = Width/10d
  final val TorchH = Width*2/5d

  final val TorchHeadX = Width*9/20d
  final val TorchHeadY = Height*3/10d
  final val TorchHeadW = Width/10d
  final val TorchHeadH = Width*1/10d

  val img = document.getElementById("mc-grass").asInstanceOf[html.Image]

  val text = "Redstone ICs".map(ch => (0 until 8).map(i => ((ch>>i) & 1) == 1)).flatten ++ Array.fill(8)(false)
  var cnt = 0

  def lit = text(cnt)
  def torchLightColour = if (lit) "#FF0000" else "#85161A"

  def drawTorch(): Unit = {
    context.beginPath()
    context.strokeStyle = torchLightColour
    context.fillStyle = torchLightColour
    context.lineWidth = 10
    context.shadowBlur = if (lit) 40 else 0
    context.shadowColor = torchLightColour
    context.rect(TorchHeadX,TorchHeadY,TorchHeadW,TorchHeadH)
    context.stroke()
    context.fill()
    context.lineWidth = 0
    context.shadowBlur = 0
  }

  def render(): Unit = {
    context.drawImage(img,0,0,240,240)
    context.fillStyle = TorchStemColour
    context.fillRect(TorchX, TorchY, TorchW, TorchH)
    drawTorch()
    cnt += 1
    cnt %= text.size
  }
}