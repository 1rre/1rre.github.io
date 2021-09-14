import org.scalajs.dom.{raw, html, document}
import projects._

object Main extends App {
  val compiler = Compiler(
    document.getElementById("compiler-terminal").asInstanceOf[html.Div]
  )
}
