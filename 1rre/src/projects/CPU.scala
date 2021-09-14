package projects
import org.scalajs.dom.{html, raw}
import util.Random

import es.tmoor.scalayout.element.canvas.Context

case class CPU(canvas: html.Canvas) extends Project("MIPS CPU", 25d) {
  final val BackgroundColour = "#3b5b6c"
  final val PcbSideLength = 120
  final val PcbX = 60
  final val PcbY = 60
  final val PcbColour = "#556C23"
  final val HeatSinkSideLength = PcbSideLength - 20
  final val HeatSinkX = PcbX + 10
  final val HeatSinkY = PcbY + 10
  final val HeatSinkCornerR = 10
  final val HeatSinkColour = "#A8B0B5"
  final val LabelFontSize = 24
  final val LabelFont = s"${LabelFontSize}px 'Lexend Exa', sans-serif"
  final val LabelX = 60 + PcbX
  final val LabelY = 60 + PcbY - LabelFontSize / 2d
  final val LabelColour = "#545E64"
  final val WireOffColour = "#BCC4C4"
  final val WireOnColour = "#45F6FF"


  final val ScalePulse = 10

  case class Wire(x1: Double, y1: Double, x2: Double, y2: Double) {
    var pulsePosition = 0d
    def pulsing = pulsePosition > 0
    final val PulseIncrement = refreshRate / 500d
    def gradient = {
      val _grad = context.createLinearGradient(x1, y1, x2, y2)
      _grad.addColorStop(0, WireOffColour)
      if (pulsing) {
        context.lineWidth = 6
        _grad.addColorStop(pulsePosition, WireOnColour)
      } else {
        context.lineWidth = 4
      }
      _grad.addColorStop(1, WireOffColour)
      _grad
    }
    def draw(): Unit = {
      context.beginPath()
      if (pulsePosition > 1) pulsePosition = 0
      context.strokeStyle = gradient
      if (pulsePosition > 0 || Random.nextDouble() > 0.975)
        pulsePosition = pulsePosition + PulseIncrement
      context.moveTo(x1, y1)
      context.lineTo(x2, y2)
      context.stroke()
    }
  }

  val layoutContext = new Context(context)

  final val Wires = Seq(
    Wire(0, 90, 60, 90),
    Wire(0, 120, 60, 120),
    Wire(0, 150, 60, 150),
    Wire(90, 0, 90, 60),
    Wire(120, 0, 120, 60),
    Wire(150, 0, 150, 60),
    Wire(240, 90, 180, 90),
    Wire(240, 120, 180, 120),
    Wire(240, 150, 180, 150),
    Wire(90, 240, 90, 180),
    Wire(120, 240, 120, 180),
    Wire(150, 240, 150, 180)
  )

  final def drawBackground(): Unit = {
    layoutContext.fill.colour = BackgroundColour
    layoutContext.drawRegularPolygon(4, 0, 0, Width, Height)
    layoutContext.fill()
  }

  final def drawPcb(): Unit = {
    layoutContext.fill.colour = PcbColour
    layoutContext.drawRegularPolygon(
      4,
      PcbX,
      PcbY,
      PcbSideLength,
      PcbSideLength
    )
    layoutContext.fill()
  }

  final def drawHeatSink(): Unit = {
    layoutContext.fill.colour = HeatSinkColour
    layoutContext.drawRoundedRectangle(
      HeatSinkX,
      HeatSinkY,
      HeatSinkSideLength,
      HeatSinkSideLength,
      HeatSinkCornerR
    )
    layoutContext.fill()
  }

  final def drawLabel(): Unit = {
    context.textAlign = "center"
    context.textBaseline = "middle"
    context.font = LabelFont
    layoutContext.fill.colour = LabelColour
    context.fillText("MIPS", LabelX, LabelY)
    context.fillText("CPU", LabelX, LabelY + LabelFontSize)
  }

  final def drawWires(): Unit = {
    Wires.foreach(_.draw())
  }

  def render(): Unit = {
    drawBackground()
    drawPcb()
    drawHeatSink()
    drawLabel()
    drawWires()
  }

  render()
}
