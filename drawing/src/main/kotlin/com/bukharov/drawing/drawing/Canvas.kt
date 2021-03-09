package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.Shape

interface Canvas {

    val size: Dimensions

    fun rasterize(): PixelLayer

    fun put(shape: Shape)

    fun shapes(): Set<Shape>

    fun print(): List<String>

    fun fill(target: Point, withColor: Pixel)
}