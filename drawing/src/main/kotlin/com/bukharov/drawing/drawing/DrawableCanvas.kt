package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.drawable.DrawableField
import com.bukharov.drawing.drawing.filling.FloodFill
import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Field
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.Shape

class DrawableCanvas(
    val size: Dimensions
) {
    var background: PixelLayer
    var vectorLayers: Field

    init {
        vectorLayers = Field(size.toUpperRightCoordinate())
        background = PixelLayer(size)
    }

    fun rasterize(): PixelLayer {
        val mergedLayers = DrawableField(field = vectorLayers)
            .rasterize()
        background = background.mergeAtop(mergedLayers)
        vectorLayers = Field(size.toUpperRightCoordinate())
        return background
    }

    fun put(shape: Shape) {
        vectorLayers.put(shape = shape)
    }

    fun fill(target: Point, withColor: Pixel) {
        background = FloodFill(rasterize()).fill(target, withColor)
    }

    fun print() =
        rasterize().print()
}