package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.drawable.DrawableField
import com.bukharov.drawing.drawing.filling.FillerFactory
import com.bukharov.drawing.drawing.filling.FloodFill4xDirection
import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Field
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.Shape

class DrawableCanvas(
    override val size: Dimensions,
    val fillerFactory: FillerFactory = FloodFill4xDirection.Factory
) : Canvas {
    private var background: PixelLayer
    private var vectorLayers: Field

    init {
        vectorLayers = Field(size.toUpperRightCoordinate())
        background = PixelLayer(size)
    }

    override fun rasterize(): PixelLayer {
        val mergedLayers = DrawableField(field = vectorLayers)
            .rasterize()
        background = background.mergeAtop(mergedLayers)
        vectorLayers = Field(size.toUpperRightCoordinate())
        return background
    }

    override fun put(shape: Shape) {
        vectorLayers.put(shape = shape)
    }

    override fun shapes() = vectorLayers.shapes()

    override fun fill(target: Point, withColor: Pixel) {
        background = fillerFactory.create(rasterize()).fill(target, withColor)
    }

    override fun print(): List<String> =
        rasterize().asStrings()
}