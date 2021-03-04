package com.bukharov.drawing.drawing.filling

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.down
import com.bukharov.drawing.geometry.left
import com.bukharov.drawing.geometry.right
import com.bukharov.drawing.geometry.up
import java.util.*

class FloodFill(
    origin: PixelLayer
) : Filler {
    val pixelLayer = origin.clone()
    private val needToExplore: Queue<Point> = LinkedList()

    override fun fill(starting: Point, fillWith: Pixel): PixelLayer {
        if (!pixelLayer.has(starting)) throw CanNotFillPointItIsOutOfCanvas(starting, pixelLayer.dimensions)
        val startingColor = pixelLayer[starting]

        needToExplore.add(starting)

        while (needToExplore.isNotEmpty()) {
            fillAndAddToQueue4xDirection(needToExplore.poll(), startingColor, fillWith)
        }

        return pixelLayer
    }

    private fun fillAndAddToQueue4xDirection(
        target: Point,
        originColor: Pixel,
        destinationPixel: Pixel
    ) {
        if (pixelLayer.has(target)) {
            if (pixelLayer[target] == originColor) {
                pixelLayer[target] = destinationPixel

                if (pixelLayer.getOrNull(target.up())?.equals(originColor) == true) {
                    needToExplore.add(target.up())
                }

                if (pixelLayer.getOrNull(target.down())?.equals(originColor) == true) {
                    needToExplore.add(target.down())
                }

                if (pixelLayer.getOrNull(target.right())?.equals(originColor) == true) {
                    needToExplore.add(target.right())
                }

                if (pixelLayer.getOrNull(target.left())?.equals(originColor) == true) {
                    needToExplore.add(target.left())
                }
            } else {
                return
            }
        } else {
            return
        }
    }
}