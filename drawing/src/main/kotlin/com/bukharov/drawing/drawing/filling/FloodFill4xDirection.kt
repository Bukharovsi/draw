package com.bukharov.drawing.drawing.filling

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.decX
import com.bukharov.drawing.geometry.decY
import com.bukharov.drawing.geometry.incX
import com.bukharov.drawing.geometry.incY
import java.util.Queue
import java.util.LinkedList

class FloodFill4xDirection(
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
        if (pixelLayer[target] != originColor) {
            return
        }
        pixelLayer[target] = destinationPixel

        if (pixelLayer.getOrNull(target.incY()) == originColor) {
            needToExplore.add(target.incY())
        }
        if (pixelLayer.getOrNull(target.decY()) == originColor) {
            needToExplore.add(target.decY())
        }
        if (pixelLayer.getOrNull(target.incX()) == originColor) {
            needToExplore.add(target.incX())
        }
        if (pixelLayer.getOrNull(target.decX()) == originColor) {
            needToExplore.add(target.decX())
        }
    }

    object Factory : FillerFactory {
        override fun create(pixelLayer: PixelLayer): Filler =
            FloodFill4xDirection(pixelLayer)
    }
}