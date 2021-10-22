package es.tmoor.website

import es.tmoor.scanvas._
import es.tmoor.scanvas.rendering.Colour

object Redstone extends Project("Redstone", "redstone-box", 250d) {
  final val nPixels = 16
  final val pixelSizeX = width / nPixels
  final val pixelSizeY = height / nPixels
  final val blockPixels = Vector(
    Vector(0x6b9e42, 0x77a648, 0x679c42, 0x72ab4a, 0x6f9a4a, 0x73a34d, 0x9d7456, 0xcf9a6e, 0xcf9a6e,
      0x6d4e3c, 0x9f7454, 0xcf9a6e, 0xcf9a6e, 0x89654f, 0xcf9a6e, 0x88644e),
    Vector(0x6f9849, 0x6d9944, 0x75a64d, 0x73a04b, 0x694d37, 0x9f7454, 0x9f7454, 0x6b4e3c, 0x9d7456,
      0x9d7454, 0x9f7454, 0x9d7456, 0x88644e, 0x89654f, 0x6d4e3c, 0x6e4f3d),
    Vector(0x79ad4b, 0x75a74e, 0x694d37, 0x694d37, 0xcf9a6e, 0x9d7456, 0x6d4e3c, 0x9f7356, 0xcf9a6e,
      0xa07457, 0x839b9f, 0x9f7356, 0x9d7456, 0x9f7356, 0x89654f, 0x89654f),
    Vector(0x6f9947, 0x71a749, 0x6a9f45, 0x72a046, 0x694d37, 0xcf9a6e, 0x9f7454, 0x88644e, 0x9f7356,
      0x6d4e3c, 0xcf9a6e, 0xcf9a6e, 0xcf9a6e, 0xcf9a6e, 0x88644e, 0x9d7456),
    Vector(0x79a84a, 0x78ab4f, 0x6f9b44, 0x77a447, 0x694d37, 0x6d4e3c, 0xcf9a6e, 0x9f7356, 0x9f7454,
      0x9f7356, 0x6d4e3c, 0x9f7356, 0x9f7356, 0x9f7356, 0xa07457, 0x9d7456),
    Vector(0x74b04c, 0x73a949, 0x694d37, 0x6d4e3c, 0x9f7454, 0xcf9a6e, 0xcf9a6e, 0xcf9a6e, 0x9f7454,
      0x89654f, 0x6d4e3c, 0x9d7456, 0xcf9a6e, 0x88644e, 0xcf9a6e, 0x9f7454),
    Vector(0x6fa246, 0x6fa649, 0x71a247, 0x694d37, 0x9d7456, 0x88644e, 0x9f7356, 0x6d4e3c, 0xcf9a6e,
      0x9d7454, 0x9d7456, 0x9d7454, 0xcf9a70, 0x9f7454, 0x6d4e3c, 0x9f7356),
    Vector(0x709746, 0x6d9a45, 0x739f48, 0x75ab4b, 0x6a4b36, 0xa07555, 0x9f7356, 0x9f7356, 0x9f7356,
      0x6d4e3a, 0xcf9a6e, 0xcf9a70, 0xcf9a6e, 0x6b4e3c, 0x9e7557, 0xa07555),
    Vector(0x75a64b, 0x79a44b, 0x719847, 0x694d37, 0x9d7456, 0xcf9a70, 0x88644e, 0x6d4e3c, 0xcf9a6e,
      0x9f7356, 0x6d4e3c, 0x9f7356, 0xa07555, 0xcf9a6e, 0xcf9a6e, 0x9f7356),
    Vector(0x699d48, 0x78ae4c, 0x694d37, 0x694d37, 0x9f7454, 0x6d4e3c, 0x9f7454, 0x859a9f, 0xcf9a70,
      0x9d7456, 0x9f7356, 0xce9b6e, 0x9f7454, 0x9d7456, 0x9f7454, 0x9d7456),
    Vector(0x75a64d, 0x694d37, 0x694d37, 0x9d7454, 0x9f7454, 0xcf9a6e, 0x6d4e3c, 0x9f7454, 0xcf9a6e,
      0x88644e, 0x9f7356, 0xcf9a6e, 0xcf9a6e, 0x6d4e3c, 0xcf9a6e, 0x859a9f),
    Vector(0x76a849, 0x689842, 0x6c9b4a, 0x684c36, 0xce9b6e, 0xcf9a6e, 0x6d4e3c, 0x9f7454, 0x88644e,
      0x9f7454, 0x9f7454, 0xcf9a6e, 0xcf9a70, 0x6d4e3c, 0x6d4e3c, 0x6d4e3c),
    Vector(0x699b44, 0x7aa74c, 0x6e9b48, 0x694d37, 0x6d4e3c, 0x9f7454, 0xcf9a70, 0xcf9a70, 0x6d4e3c,
      0x6d4e3c, 0x9f7454, 0xcf9a70, 0x6d4e3c, 0x6e4f3b, 0x9f7356, 0x6d4e3c),
    Vector(0x6c9547, 0x7caa50, 0x70a549, 0x684c36, 0x9e7557, 0xcf9a6e, 0xcf9a6e, 0x9f7454, 0xcf9a6e,
      0x6d4e3c, 0x6e4f3d, 0xcf9a6e, 0xcf9a6e, 0x9d7454, 0x9e7555, 0xcf9a70),
    Vector(0x77a84c, 0x70a04a, 0x74ae4a, 0x694d37, 0xce9b6e, 0xcf9a6e, 0x6e4f3d, 0xa07555, 0x9f7454,
      0xcf9a6e, 0x9f7454, 0x6d4e3c, 0xcf9a6e, 0x89654f, 0x6d4e3a, 0x9f7454),
    Vector(0x6b9847, 0x7aa74c, 0x76a74c, 0x75a849, 0x694d37, 0x694d37, 0xd09b6f, 0x9f7356, 0x6d4e3c,
      0xcf9a70, 0x6d4e3c, 0x88644e, 0xcf9a70, 0x9f7356, 0x9f7356, 0x9d7454)
  ).map(_.map(Colour(_)))
  object Torch extends SubTemplate {
    final val colour = Colour(0xe0b877)
    def relativeBounds: BoundingBox.BoundingBox = (9 / 20d, 3 / 10d, 1 / 10d, 1 / 2d)
    object TorchHead extends SubTemplate {
      final val glowSize = 1.4 * bounds._3
      final val glowColour = Colour(0xff0000)
      final val headColour = Colour(0x75161a)
      final val borderSize = bounds._3 / 2.4
      // Text to array of booleans spelling "Redstone ICs"
      final val text =
        "Redstone ICs\u0000".map(ch => (0 until 8).map(i => ((ch >> i) & 1) == 1)).flatten
      var textIt = text.iterator
      def relativeBounds: BoundingBox.BoundingBox = (0, 0, 1, 1 / 5d)
      def children: Seq[Template] = Nil

      def lit() =
        if (textIt.hasNext) textIt.next()
        else {
          textIt = text.iterator
          textIt.next()
        }
      
      def draw(): Unit = {
        if (lit()) {
          context.Glow.size = glowSize
          context.Glow.colour = glowColour
          context.Draw.colour = glowColour
          context.Draw.thickness = borderSize
          context.Draw.regularPoly(4, bounds)
          context.Glow.size = 0
          context.Fill.colour = glowColour
          context.Fill.regularPoly(4, bounds)
        } else {
          context.Draw.colour = headColour
          context.Draw.thickness = borderSize
          context.Draw.regularPoly(4, bounds)
          context.Fill.colour = headColour
          context.Fill.regularPoly(4, bounds)
        }
      }
    }
    def children: Seq[Template] = Seq(TorchHead)
    def draw(): Unit = {
      context.Fill.colour = colour
      context.Fill.regularPoly(4, bounds)
    }
  }
  def children: Seq[Template] = Seq(Torch)
  def draw(): Unit = {
    for (i <- blockPixels.indices; j <- blockPixels(i).indices) {
      context.Fill.colour = blockPixels(i)(j)
      val (x, y, w, h) = bounds
      context.Fill.regularPoly(
        4,
        (x + i * pixelSizeX, y + j * pixelSizeY, pixelSizeX, pixelSizeY)
      )
    }
  }
}
