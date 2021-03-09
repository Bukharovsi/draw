package com.bukharov.drawing.drawing.pixel

@Suppress("TooManyFunctions")
internal class PixelRow private constructor(
    private val canvas: Array<Pixel>,
    private val length: Int
) : Iterable<Pixel>, Cloneable {

    internal constructor(
        length: Int,
        fillWith: Pixel = Pixel.Empty
    ) : this(canvas = Array(length) { fillWith }, length = length)

    override fun iterator(): Iterator<Pixel> =
        canvas.iterator()

    operator fun set(i: Int, newPixel: Pixel) {
        if (!has(i)) throw PixelDoesNotExist(needed = i, boundaries = canvas.lastIndex)
        else canvas[i] = newPixel
    }

    operator fun get(i: Int) =
        if (!has(i)) throw PixelDoesNotExist(needed = i, boundaries = canvas.lastIndex)
        else canvas[i]

    fun getOrNull(i: Int) =
        if (!has(i)) null
        else canvas[i]

    fun has(i: Int): Boolean =
        0 <= i && i <= canvas.lastIndex

    fun mergeAtop(above: PixelRow): PixelRow {
        val merged = PixelRow(maxOf(this.length, above.length))
        // copy existing
        this.forEachIndexed { i, _ -> merged[i] = this[i] }
        // copy above
        above.forEachIndexed {
                i, pixelAbove -> merged[i] = (getOrNull(i) ?: merged[i]).mergeAtop(pixelAbove) }
        return merged
    }

    public override fun clone(): PixelRow = PixelRow(canvas, length)

    fun asString(): String =
        CharArray(length, { i -> this[i].print() })
            .joinToString(separator = "")

    override fun toString(): String {
        return "pl(" + CharArray(canvas.size) { i -> canvas[i].print() }.concatToString() + ")"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is PixelRow) return true
        if (!canvas.contentEquals(other.canvas)) return false
        return true
    }

    override fun hashCode(): Int {
        return canvas.contentHashCode()
    }

    companion object {
        fun create(length: Int, fillWith: Pixel = Pixel.Empty) =
            if (length < 1) {
                throw LineLengthShouldBePositiveValue(length)
            } else {
                PixelRow(length, fillWith)
        }
    }
}

class LineLengthShouldBePositiveValue(val length: Int) : IllegalArgumentException()
data class PixelDoesNotExist(val needed: Int, val boundaries: Int) : IllegalStateException()
