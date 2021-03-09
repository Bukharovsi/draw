package com.bukharov.drawing.drawing.filling

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.UserReadableError

interface Filler {
    fun fill(starting: Point, fillWith: Pixel): PixelLayer
}

interface FillerFactory {
    fun create(pixelLayer: PixelLayer): Filler
}

class CanNotFillPointItIsOutOfCanvas(
    val target: Point,
    val size: Dimensions
) : UserReadableError, IllegalArgumentException() {
    override fun message() = "Target point $target is out of canvas $size"
}