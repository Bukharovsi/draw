package com.bukharov.drawing.drawing.drawable

import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Canvas
import com.bukharov.drawing.geometry.toDimension

// add background?
class DrawableCanvas(
    val canvas: Canvas
) {
    private val size = canvas.rightUpperCorner.toDimension()
    private val back = PixelLayer.create(size)

    fun rasterize(): PixelLayer {
        val imagesOfShapes = canvas.shapes()
            .map { shape -> DrawableLine(shape).rasterize() }

        val all = mutableListOf(back)
            .apply { addAll(imagesOfShapes) }
        return all.reduce { acc, layer -> acc.mergeAtop(layer) }
        // использовать в канвас предыдущий законченный слой как бекграунд для нового.
    }
}