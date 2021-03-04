package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Shape

interface Canvas {
    fun rasterize(): PixelLayer

    fun put(shape: Shape)

    fun print(): String
}