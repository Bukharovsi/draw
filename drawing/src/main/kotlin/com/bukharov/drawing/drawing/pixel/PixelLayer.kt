package com.bukharov.drawing.drawing.pixel

import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point

class PixelLayer private constructor(
    private val rows: Array<PixelRow>,
    override val dimensions: Dimensions,
) : Cloneable, Printable {

    constructor(
        dimensions: Dimensions,
        fillWith: Pixel = Pixel.Empty
    ) : this(
        rows = Array(dimensions.height) { PixelRow(dimensions.width, fillWith) },
        dimensions = dimensions
    )

    fun has(coordinate: Point): Boolean {
        if (0 > coordinate.y || coordinate.y > rows.lastIndex) return false
        return rows[coordinate.y].has(coordinate.x)
    }

    operator fun get(coordinate: Point): Pixel {
        if (!has(coordinate)) throw LayerPixelDoesNotExist(
            needed = coordinate,
            boundaries = dimensions.toUpperRightCoordinate()
        )
        return rows[coordinate.y][coordinate.x]
    }

    fun getOrNull(coordinate: Point): Pixel? {
        if (!has(coordinate)) return null
        return rows[coordinate.y][coordinate.x]
    }

    operator fun set(coordinate: Point, pixel: Pixel) {
        if (!has(coordinate)) throw LayerPixelDoesNotExist(
            needed = coordinate,
            boundaries = dimensions.toUpperRightCoordinate()
        )
        rows[coordinate.y].set(coordinate.x, pixel)
    }

    fun mergeAtop(aboveLayer: PixelLayer): PixelLayer {
        if (aboveLayer.dimensions.moreByAnyDirectionThan(this.dimensions)) {
            throw LayersHaveDifferentSize(
                background = this.dimensions,
                above = aboveLayer.dimensions)
        }

        val merged = this.clone()
        aboveLayer.rows
            .mapIndexed { index, lineAbove -> this.rows[index].mergeAtop(lineAbove) }
            .forEachIndexed { index, mergedLine -> merged.rows[index] = mergedLine }
        return merged
    }

    override fun asStrings(): List<String> =
        rows.map {
            it.print()
        }.toList()

    override fun toString(): String {
        return "PixelLayer(lines=${rows.contentToString()})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PixelLayer) return false
        if (!rows.contentEquals(other.rows)) return false
        return true
    }

    override fun hashCode(): Int {
        return rows.contentHashCode()
    }

    public override fun clone() = PixelLayer(rows.map { it.clone() }.toTypedArray(), dimensions)
}

data class LayerPixelDoesNotExist(val needed: Point, val boundaries: Point) : IllegalStateException()
data class LayersHaveDifferentSize(val background: Dimensions, val above: Dimensions) : IllegalStateException()