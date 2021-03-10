package com.bukharov.drawing.drawing.pixel

import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.UserReadableError
import com.bukharov.drawing.geometry.maxOfDimensions

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
        if (!has(coordinate)) throw LayerPixelDoesNotExist(needed = coordinate)
        return rows[coordinate.y][coordinate.x]
    }

    fun getOrNull(coordinate: Point): Pixel? {
        if (!has(coordinate)) return null
        return rows[coordinate.y][coordinate.x]
    }

    operator fun set(coordinate: Point, pixel: Pixel) {
        if (!has(coordinate)) throw LayerPixelDoesNotExist(needed = coordinate)
        rows[coordinate.y][coordinate.x] = pixel
    }

    fun mergeAtop(aboveLayer: PixelLayer): PixelLayer {
        val merged = PixelLayer(maxOfDimensions(dimensions, aboveLayer.dimensions))

        // fill merged canvas using `this` as origin
        this.rows.forEachIndexed {
                i, originRow -> merged.rows[i] = merged.rows[i].mergeAtop(originRow)
        }

        // fill merged canvas merging this and above layer
        aboveLayer.rows.forEachIndexed {
                i, lineAbove -> merged.rows[i] = merged.rows[i].mergeAtop(lineAbove)
        }
        return merged
    }

    override fun asStrings(): List<String> =
        rows.map {
            it.asString()
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

data class LayerPixelDoesNotExist(val needed: Point) : UserReadableError, IllegalStateException() {
    override fun message() = "pixel with coordinate $needed does not exist"
}