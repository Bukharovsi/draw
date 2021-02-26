package com.bukharov.drawing.drawing

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.bukharov.drawing.geometry.DrawingError
import java.io.PrintStream

class PixelLine internal constructor(
    length: Int
) : Iterable<Pixel> {
    private val canvas: Array<Pixel> = Array(length) { Pixel.Empty }

    override fun iterator(): Iterator<Pixel> =
        canvas.iterator()

    fun changePixel(i: Int, newPixel: Pixel): Either<DrawingError, PixelLine> {
        if (!has(i)) return PixelDoesNotExist.left()
        else canvas[i] = newPixel

        return this.right()
    }

    operator fun get(i: Int): Either<PixelDoesNotExist, Pixel> =
        if (!has(i)) {
            PixelDoesNotExist.left()
        } else {
            canvas[i].right()
        }

    fun has(i: Int): Boolean =
        0 <= i && i <= canvas.lastIndex

    fun drawTo(stream: PrintStream) {
        val charLine: CharArray = CharArray(canvas.size) { i -> canvas[i].print() }
        stream.print(charLine)
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
object PixelDoesNotExist : DrawingError