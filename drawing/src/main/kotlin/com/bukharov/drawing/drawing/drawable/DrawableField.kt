package com.bukharov.drawing.drawing.drawable

import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Field
import com.bukharov.drawing.geometry.toDimension

class DrawableField(
    val field: Field
) : Drawable {
    private val size = field.rightUpperCorner.toDimension()
    private val emptyBackground = PixelLayer.create(size)

    override fun rasterize(): PixelLayer {
        val imagesOfShapes =
            mutableListOf(emptyBackground) + field
                .shapes()
                .map { shape -> DrawableLine(shape).rasterize() }

        return imagesOfShapes.reduce { acc, layer -> acc.mergeAtop(layer) }
    }
}