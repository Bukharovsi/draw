package com.bukharov.drawing.drawing.drawable

import com.bukharov.drawing.drawing.pixel.PixelLayer

interface Drawable {
    fun rasterize(): PixelLayer
}