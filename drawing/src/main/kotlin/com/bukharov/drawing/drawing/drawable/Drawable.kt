package com.bukharov.drawing.drawing.drawable

import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Shape

interface Drawable {
    fun rasterize(): PixelLayer
}

class ShapeCanNotBeRasterized(val shape: Shape) : IllegalStateException()