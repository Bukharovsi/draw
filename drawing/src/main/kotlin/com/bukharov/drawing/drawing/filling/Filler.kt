package com.bukharov.drawing.drawing.filling

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point

interface Filler {
    fun fill(starting: Point, fillWith: Pixel): PixelLayer
}

class CanNotFillPointItIsOutOfCanvas(target: Point, size: Dimensions): IllegalArgumentException()