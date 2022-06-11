import mill._, scalalib._, scalajslib._

object SCanvas extends ScalaJSModule {
  def scalaVersion = "3.1.1"
  def scalaJSVersion = "1.10.0"
  def scalacOptions = Seq("-deprecation")
  def ivyDeps = Agg(ivy"org.scala-js:scalajs-dom_sjs1_3:2.2.0")
}
object `1rre` extends ScalaJSModule {
  def scalaVersion = "3.1.1"
  def scalaJSVersion = "1.10.0"
  def scalacOptions = Seq("-deprecation")
  def ivyDeps = Agg(ivy"org.scala-js:scalajs-dom_sjs1_3:2.2.0")
  def moduleDeps = Seq(SCanvas)
}
