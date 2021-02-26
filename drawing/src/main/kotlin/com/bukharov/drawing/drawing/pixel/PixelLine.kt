package com.bukharov.drawing.drawing.pixel

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.bukharov.drawing.geometry.DrawingError
import java.io.PrintStream

internal class PixelLine internal constructor(
    length: Int
) : Iterable<Pixel> {
    private val canvas: Array<Pixel> = Array(length) { Pixel.Empty }

    override fun iterator(): Iterator<Pixel> =
        canvas.iterator()

    fun changePixel(i: Int, newPixel: Pixel): Either<DrawingError, PixelLine> {
        if (!has(i)) return PixelDoesNotExist(needed = i, boundaries = canvas.lastIndex).left()
        else canvas[i] = newPixel

        return this.right()
    }

    operator fun get(i: Int): Either<DrawingError, Pixel> =
        if (!has(i)) {
            PixelDoesNotExist(needed = i, boundaries = canvas.lastIndex).left()
        } else {
            canvas[i].right()
        }

    fun has(i: Int): Boolean =
        0 <= i && i <= canvas.lastIndex

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
        fun create(length: Int): Either<DrawingError, PixelLine> =
            if (length < 1) {
                LineLengthShouldBePositiveValue.left()
            } else {
                PixelLine(length).right()
        }
    }
}

object LineLengthShouldBePositiveValue : DrawingError
data class PixelDoesNotExist<T>(val needed: T, val boundaries: T) : DrawingError
