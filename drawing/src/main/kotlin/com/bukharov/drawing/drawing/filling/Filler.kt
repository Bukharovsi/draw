package com.bukharov.drawing.drawing.filling

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point

interface Filler {
    fun fill(starting: Point, fillWith: Pixel): PixelLayer
}

interface FillerFactory {
    fun create(pixelLayer: PixelLayer): Filler
}

class CanNotFillPointItIsOutOfCanvas(
    val target: Point,
    val size: Dimensions
) : IllegalArgumentException()