package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Canvas

class DrawableCanvas(
    // val background: PixelLayer
    val canvas: Canvas
) {

    fun draw(): PixelLayer {
        canvas.shapes()
            .map { shape -> DrawableLine(shape).rasterize() }
            // использовать в канвас предыдущий законченный слой как бекграунд для нового.

        TODO()
    }
}