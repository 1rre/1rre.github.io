import mill._, scalalib._, scalajslib._

object `1rre` extends ScalaJSModule {
  def scalaJSVersion = "1.6.0"
  def scalaVersion = "2.13.6"
  def ivyDeps = Agg(ivy"org.scala-js:scalajs-dom_sjs1_2.13:1.1.0")
}