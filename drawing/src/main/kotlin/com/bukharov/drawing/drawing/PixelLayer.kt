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

    fun has(coordinate: Point): Boolean {
        if (0 > coordinate.y || coordinate.y > lines.lastIndex) return false
        return lines[coordinate.y].has(coordinate.x)
    }

    fun get(coordinate: Point): Either<PixelDoesNotExist, Pixel> {
        if (!has(coordinate)) return PixelDoesNotExist.left()
        return lines[coordinate.y][coordinate.x]
    }

    fun change(coordinate: Point, pixel: Pixel): Either<PixelDoesNotExist, PixelLayer> {
        if (!has(coordinate)) return PixelDoesNotExist.left()
        lines[coordinate.y].changePixel(coordinate.x, pixel)
        return this.right()
    }

    companion object {
        fun create(width: Int, height: Int) =
            if (height <= 0 || width <= 0) WrongWidthAndHeight.left()
            else PixelLayer(width = width, height = height).right()
    }
}

object WrongWidthAndHeight : DrawingError