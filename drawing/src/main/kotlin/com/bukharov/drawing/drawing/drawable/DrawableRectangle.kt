package com.bukharov.drawing.drawing.drawable

import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Rectangle

class DrawableRectangle(
    private val rectangle: Rectangle
) : Drawable {
    override fun rasterize(): PixelLayer =
        rectangle
            .edges()
            .map { edge -> DrawableLine(edge) }
            .map { line -> line.rasterize() }
            .reduce { acc, layer -> acc.mergeAtop(layer) }
}