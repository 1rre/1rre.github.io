package es.tmoor.website

import es.tmoor.scanvas.Template
import es.tmoor.scanvas.rendering.Colour

object Rover extends Project("EEE Rover", "rover-box", 50d) {
  val colour = Colour(0x1B671A)
  def children: Seq[Template] = Nil  
  def draw(): Unit = {
    context.Fill.colour = colour
    context.Fill.regularPoly(4, bounds)
  }
}