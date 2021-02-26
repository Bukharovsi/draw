package com.bukharov.drawing.drawing.pixel

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.bukharov.drawing.geometry.DrawingError
import com.bukharov.drawing.geometry.Point
import java.io.PrintStream

class PixelLayer(
    private val width: Int,
    private val height: Int
) {
    private val lines: Array<PixelLine> = Array(height) { PixelLine(width) }

    fun has(coordinate: Point): Boolean {
        if (0 > coordinate.y || coordinate.y > lines.lastIndex) return false
        return lines[coordinate.y].has(coordinate.x)
    }

    fun get(coordinate: Point): Either<DrawingError, Pixel> {
        if (!has(coordinate)) return PixelDoesNotExist(
            needed = coordinate,
            boundaries = Point(x = width, y = height)
        ).left()
        return lines[coordinate.y][coordinate.x]
    }

    fun change(coordinate: Point, pixel: Pixel): Either<DrawingError, PixelLayer> {
        if (!has(coordinate)) return PixelDoesNotExist(
            needed = coordinate,
            boundaries = Point(x = width, y = height)
        ).left()
        lines[coordinate.y].changePixel(coordinate.x, pixel)
        return this.right()
    }

    fun drawTo(stream: PrintStream) {
        lines.forEach {
            it.drawTo(stream)
            stream.print("\n")
        }
    }



    override fun toString(): String {
        return "PixelLayer(lines=${lines.contentToString()})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PixelLayer) return false
        if (!lines.contentEquals(other.lines)) return false
        return true
    }

    override fun hashCode(): Int {
        return lines.contentHashCode()
    }

    companion object {
        fun create(width: Int, height: Int) =
            if (height <= 0 || width <= 0) WrongWidthAndHeight.left()
            else PixelLayer(width = width, height = height).right()
    }
}

object WrongWidthAndHeight : DrawingError