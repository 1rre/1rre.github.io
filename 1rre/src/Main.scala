import org.scalajs.dom.{raw, html, document}
import projects._

object Main extends App {
  val compiler = Compiler(
    document.getElementById("compiler-box").asInstanceOf[html.Canvas]
  )
  val cpu = CPU(
    document.getElementById("cpu-box").asInstanceOf[html.Canvas]
  )
  val redstone = Redstone(
    document.getElementById("redstone-box").asInstanceOf[html.Canvas]
  )
  val pong = Pong(
    document.getElementById("pong-box").asInstanceOf[html.Canvas]
  )
  val rover = Rover(
    document.getElementById("rover-box").asInstanceOf[html.Canvas]
  )
  val parser = Parser(
    document.getElementById("parser-box").asInstanceOf[html.Canvas]
  )
  val website = Website(
    document.getElementById("website-box").asInstanceOf[html.Canvas]
  )
  val circuit = Circuit(
    document.getElementById("circuit-box").asInstanceOf[html.Canvas]
  )
}
