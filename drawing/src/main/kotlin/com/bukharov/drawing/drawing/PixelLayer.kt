package com.bukharov.drawing.drawing

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.bukharov.drawing.geometry.DrawingError
import com.bukharov.drawing.geometry.Point

class PixelLayer(
    width: Int,
    height: Int
) {
    private val lines: Array<PixelLine> = Array(height) { PixelLine(width) }

    fun has(x: Int, y:Int): Boolean {
        if (0 > y || y > lines.lastIndex) return false
        return lines[y].has(x)
    }

    fun get(coordinate: Point): Either<DrawingError, Pixel> {
        if (!has(coordinate.x, coordinate.y)) return PixelDoesNotExist.left()
        return lines[coordinate.y][coordinate.x]
    }

    fun change(point: Point, pixel: Pixel): Either<DrawingError, PixelLayer> {
        lines[point.y].changePixel(point.x, pixel)
        return this.right()
    }

    companion object {
        fun create(width: Int, height: Int) =
            if (height <= 0 || width <= 0) WrongWidthAndHeight.left()
            else PixelLayer(width = width, height = height).right()
    }
}

object WrongWidthAndHeight: DrawingError