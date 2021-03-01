package com.bukharov.drawing.drawing.pixel

import java.io.PrintStream

internal class PixelLine internal constructor(
    private val length: Int
) : Iterable<Pixel> {
    private val canvas: Array<Pixel> = Array(length) { Pixel.Empty }

    override fun iterator(): Iterator<Pixel> =
        canvas.iterator()

    fun changePixel(i: Int, newPixel: Pixel): PixelLine {
        if (!has(i)) throw PixelDoesNotExist(needed = i, boundaries = canvas.lastIndex)
        else canvas[i] = newPixel
        return this
    }

    operator fun get(i: Int) =
        if (!has(i)) throw PixelDoesNotExist(needed = i, boundaries = canvas.lastIndex)
        else canvas[i]

    fun has(i: Int): Boolean =
        0 <= i && i <= canvas.lastIndex

    fun mergeAtop(above: PixelLine): PixelLine {
        if (this.length != above.length) throw LinesCanNotBeMerged(this.length, above.length)
        val merged = PixelLine(length)
        above
            .mapIndexed { index: Int, pixelAbove: Pixel -> this.canvas[index].mergeAtop(pixelAbove) }
            .mapIndexed { index: Int, mergedPixel: Pixel -> merged.changePixel(index, mergedPixel) }

        return merged
    }

    fun drawTo(stream: PrintStream) {
        val charLine: CharArray = CharArray(canvas.size) { i -> canvas[i].print() }
        stream.print(charLine)
    }

    override fun toString(): String {
        return "pl(" + CharArray(canvas.size) { i -> canvas[i].print() }.concatToString() + ")"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is PixelLine) return true
        if (!canvas.contentEquals(other.canvas)) return false
        return true
    }

    override fun hashCode(): Int {
        return canvas.contentHashCode()
    }

    companion object {
        fun create(length: Int) =
            if (length < 1) {
                throw LineLengthShouldBePositiveValue(length)
            } else {
                PixelLine(length)
        }
    }
}

class LineLengthShouldBePositiveValue(val length: Int) : IllegalArgumentException()
class LinesCanNotBeMerged(val line1Length: Int, val line2Length: Int) : IllegalStateException()
data class PixelDoesNotExist(val needed: Int, val boundaries: Int) : IllegalStateException()
