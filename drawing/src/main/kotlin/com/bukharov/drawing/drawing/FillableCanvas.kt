package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.filling.FloodFill
import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Point

class FillableCanvas(
    private val canvas: Canvas
) : Canvas by canvas {

    var background: PixelLayer = PixelLayer(canvas.size)

    fun fill(target: Point, withColor: Pixel) {
        background = FloodFill(rasterize()).fill(target, withColor)
    }

    override fun rasterize(): PixelLayer {
        return background.mergeAtop(canvas.rasterize())
    }

    override fun print(): String {
        return rasterize().print()
    }
}