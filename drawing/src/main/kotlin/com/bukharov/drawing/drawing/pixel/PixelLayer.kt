package com.bukharov.drawing.drawing.pixel

import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point

class PixelLayer private constructor(
    private val lines: Array<PixelLine>,
    val dimensions: Dimensions,
) : Cloneable, Printable {

    internal constructor(
        dimensions: Dimensions,
        fillWith: Pixel = Pixel.Empty
    ) : this(
        lines = Array(dimensions.height) { PixelLine(dimensions.width, fillWith) },
        dimensions = dimensions
    )

    fun has(coordinate: Point): Boolean {
        if (0 > coordinate.y || coordinate.y > lines.lastIndex) return false
        return lines[coordinate.y].has(coordinate.x)
    }

    operator fun get(coordinate: Point): Pixel {
        if (!has(coordinate)) throw LayerPixelDoesNotExist(
            needed = coordinate,
            boundaries = dimensions.toUpperRightCoordinate()
        )
        return lines[coordinate.y][coordinate.x]
    }

    fun getOrNull(coordinate: Point): Pixel? {
        if (!has(coordinate)) return null
        return lines[coordinate.y][coordinate.x]
    }

    operator fun set(coordinate: Point, pixel: Pixel) {
        if (!has(coordinate)) throw LayerPixelDoesNotExist(
            needed = coordinate,
            boundaries = dimensions.toUpperRightCoordinate()
        )
        lines[coordinate.y].changePixel(coordinate.x, pixel)
    }

    fun mergeAtop(aboveLayer: PixelLayer): PixelLayer {
        if (aboveLayer.dimensions.moreByAnyDirectionThan(this.dimensions)) {
            throw LayersHaveDifferentSize(
                background = this.dimensions,
                above = aboveLayer.dimensions)
        }

        val merged = this.clone()
        aboveLayer.lines
            .mapIndexed { index, lineAbove -> this.lines[index].mergeAtop(lineAbove) }
            .mapIndexed { index, mergedLine -> merged.lines[index] = mergedLine }
        return merged
    }

    override fun print() =
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

    public override fun clone() = PixelLayer(lines.map { it.clone() }.toTypedArray(), dimensions)

    companion object {
        fun create(width: Int, height: Int, fillWith: Pixel = Pixel.Empty) =
            Dimensions
                .create(width = width, height = height)
                .let { dimensions -> PixelLayer(dimensions, fillWith) }

        fun create(size: Dimensions, fillWith: Pixel = Pixel.Empty) =
             PixelLayer(size, fillWith)
    }
}

data class LayerPixelDoesNotExist(val needed: Point, val boundaries: Point) : IllegalStateException()
data class LayersHaveDifferentSize(val background: Dimensions, val above: Dimensions) : IllegalStateException()