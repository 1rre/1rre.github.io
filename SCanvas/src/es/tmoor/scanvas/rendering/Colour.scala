package es.tmoor.scanvas.rendering

object Colour {
  def apply(r: Int, g: Int, b: Int) = f"#$r%02x$g%02x$b%02x"
  def apply(c: Int) = f"#$c%06x"
}
