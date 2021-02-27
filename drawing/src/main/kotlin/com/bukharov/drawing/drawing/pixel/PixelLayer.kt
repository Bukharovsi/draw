package com.bukharov.drawing.drawing.pixel

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.DrawingError
import com.bukharov.drawing.geometry.Point
import java.io.PrintStream

class PixelLayer(
    private val dimensions: Dimensions
) {
    private val lines: Array<PixelLine> = Array(dimensions.height) { PixelLine(dimensions.width) }

    fun has(coordinate: Point): Boolean {
        if (0 > coordinate.y || coordinate.y > lines.lastIndex) return false
        return lines[coordinate.y].has(coordinate.x)
    }

    fun get(coordinate: Point): Either<DrawingError, Pixel> {
        if (!has(coordinate)) return PixelDoesNotExist(
            needed = coordinate,
            boundaries = dimensions.toUpperRightCoordinate()
        ).left()
        return lines[coordinate.y][coordinate.x]
    }

    fun change(coordinate: Point, pixel: Pixel): Either<DrawingError, PixelLayer> {
        if (!has(coordinate)) return PixelDoesNotExist(
            needed = coordinate,
            boundaries = dimensions.toUpperRightCoordinate()
        ).left()
        lines[coordinate.y].changePixel(coordinate.x, pixel)
        return this.right()
    }

//    fun mergeAtop(aboveLayer: PixelLayer): PixelLayer {
//        TODO("need to remove either")
//        aboveLayer.lines.size > 0
//        val merged = PixelLayer(dimensions)
//        aboveLayer.lines
//            .mapIndexed { index, lineAbove -> this.lines[index].mergeAtop(lineAbove) }
//            .mapIndexed { index, either -> either.map { merged.get(index) } }
//    }

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
            Dimensions
                .create(width = width, height = height)
                .let { dimensions -> PixelLayer(dimensions) }
                .right()
    }
}

object WrongWidthAndHeight : DrawingError