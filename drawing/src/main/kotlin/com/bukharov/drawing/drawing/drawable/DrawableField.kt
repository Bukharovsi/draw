package com.bukharov.drawing.drawing.drawable

import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Field
import com.bukharov.drawing.geometry.Line
import com.bukharov.drawing.geometry.Rectangle
import com.bukharov.drawing.geometry.toDimension

/**
 * Draw a pixel representation of a geometric field containing shapes
 */
class DrawableField(
    val field: Field
) : Drawable {
    private val size = field.upperRightCorner.toDimension()
    private val emptyBackground = PixelLayer(size)

    override fun rasterize(): PixelLayer {
        val layersOfShapes: List<PixelLayer> =
            mutableListOf(emptyBackground) + field
                .shapes()
                .map { shape ->
                    when (shape) {
                        is Line -> DrawableLine(shape).rasterize()
                        is Rectangle -> DrawableRectangle(shape).rasterize()
                        else -> throw ShapeCanNotBeRasterized(shape)
                    }
                }

        return layersOfShapes.reduce { acc, layer -> acc.mergeAtop(layer) }
    }
}