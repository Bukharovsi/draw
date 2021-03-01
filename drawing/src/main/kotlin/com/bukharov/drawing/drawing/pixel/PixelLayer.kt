package com.bukharov.drawing.drawing.pixel

import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point

class PixelLayer(
    private val dimensions: Dimensions,
    fillWith: Pixel = Pixel.Empty
) {
    private val lines: Array<PixelLine> = Array(dimensions.height) { PixelLine(dimensions.width, fillWith) }

    fun has(coordinate: Point): Boolean {
        if (0 > coordinate.y || coordinate.y > lines.lastIndex) return false
        return lines[coordinate.y].has(coordinate.x)
    }

    fun get(coordinate: Point): Pixel {
        if (!has(coordinate)) throw LayerPixelDoesNotExist(
            needed = coordinate,
            boundaries = dimensions.toUpperRightCoordinate()
        )
        return lines[coordinate.y][coordinate.x]
    }

    fun change(coordinate: Point, pixel: Pixel): PixelLayer {
        if (!has(coordinate)) throw LayerPixelDoesNotExist(
            needed = coordinate,
            boundaries = dimensions.toUpperRightCoordinate()
        )
        lines[coordinate.y].changePixel(coordinate.x, pixel)
        return this
    }

    fun mergeAtop(aboveLayer: PixelLayer): PixelLayer {
        if (aboveLayer.dimensions != this.dimensions) throw LayersHaveDifferentSize(this, aboveLayer)
        val merged = PixelLayer(dimensions)
        aboveLayer.lines
            .mapIndexed { index, lineAbove -> this.lines[index].mergeAtop(lineAbove) }
            .mapIndexed { index, mergedLine -> merged.lines[index] = mergedLine }
        return merged
    }

    fun print() =
        lines.joinToString(separator = "") {
            it.print() + "\n"
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
        fun create(width: Int, height: Int, fillWith: Pixel = Pixel.Empty) =
            Dimensions
                .create(width = width, height = height)
                .let { dimensions -> PixelLayer(dimensions, fillWith) }
    }
}

data class LayerPixelDoesNotExist(val needed: Point, val boundaries: Point) : IllegalStateException()
data class LayersHaveDifferentSize(val layer1: PixelLayer, val layer2: PixelLayer): IllegalStateException()