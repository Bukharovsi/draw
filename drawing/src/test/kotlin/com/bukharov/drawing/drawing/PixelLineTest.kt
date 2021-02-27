package com.bukharov.drawing.drawing

import arrow.core.extensions.either.apply.tupled
import arrow.core.flatMap
import com.bukharov.drawing.drawing.pixel.LineLengthShouldBePositiveValue
import com.bukharov.drawing.drawing.pixel.LinesCanNotBeMerged
import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelDoesNotExist
import com.bukharov.drawing.drawing.pixel.PixelLine
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeLeftOfType
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class PixelLineTest {

    @Test
    fun `new created pixel line should have mnot more than length pixels`() {
        PixelLine.create(2) shouldBeRight { line ->
            line.iterator()
                .also { it.next() }
                .also { it.next() }
                .also { it.hasNext() shouldBe false }
        }
    }

    @Test
    fun `new created pixel line has all empty pixels`() {
        PixelLine.create(5)
            .shouldBeRight {
                it.forEach { pixel -> pixel shouldBe Pixel.Empty }
            }
    }

    @Test
    fun `pixelLine can not be created with 0 length`() {
        PixelLine.create(0) shouldBeLeft LineLengthShouldBePositiveValue
    }

    @Test
    fun `pixelLine can not be created with negative length`() {
        PixelLine.create(-1) shouldBeLeft LineLengthShouldBePositiveValue
    }

    @Test
    fun `pixel can not be retrievable by index if id does not exist`() {
        PixelLine.create(length = 3) shouldBeRight { pixelLine ->
            pixelLine[3].shouldBeLeftOfType<PixelDoesNotExist<Int>>()
        }
    }

    @Test
    fun `pixel can be retrievable by index if it exists`() {
        PixelLine.create(length = 3) shouldBeRight { pixelLine ->
            pixelLine[2] shouldBeRight Pixel.Empty
        }
    }

    @Test
    fun `pixel with negative index does not exist`() {
        PixelLine.create(5) shouldBeRight {
            it.has(-1) shouldBe false
        }
    }

    @Test
    fun `pixel with index out of range of line does not exist`() {
        PixelLine.create(5) shouldBeRight {
            it.has(5) shouldBe false
        }
    }

    @Test
    fun `pixel with index inside range of line exist`() {
        PixelLine.create(5) shouldBeRight {
            it.has(3) shouldBe true
        }
    }

    @Test
    fun `when pixel has not been changed then Error`() {
        PixelLine
            .create(5)
            .flatMap { it.changePixel(6, Pixel.X) }
            .shouldBeLeftOfType<PixelDoesNotExist<Int>>()
    }

    @Test
    fun `when pixel is changed successfully then it will be retrievable`() {
        val newPixel = Pixel.X
        PixelLine
            .create(5)
            .flatMap { it.changePixel(1, newPixel) }
            .fold(
                ifRight = { pixelLine -> pixelLine[1] shouldBeRight newPixel },
                ifLeft = { fail("Can not change pixel") }
            )
    }

    @Test
    fun `empty pixel line should be printed as all blanks`() {
        val byteStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteStream)
        PixelLine.create(5) shouldBeRight { pixelLine ->
            pixelLine.drawTo(printStream)
        }

        val printedStream = String(byteStream.toByteArray())
        val expectedString = "     " // 5 chars
        printedStream shouldBe expectedString
    }

    @Test
    fun `not empty pixel line should be printed right`() {
        val byteStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteStream)
        PixelLine.create(5) shouldBeRight { pixelLine ->
            pixelLine.changePixel(1, Pixel.X)
            pixelLine.changePixel(2, Pixel.X)

            pixelLine.drawTo(printStream)
        }

        val printedStream = String(byteStream.toByteArray())
        val expectedString = " xx  " // 5 chars
        printedStream shouldBe expectedString
    }

    @Test
    fun `two empty pixel lines are equal`() {
        PixelLine.create(4) shouldBeRight PixelLine(4)
    }

    @Test
    fun `two identical pixel lines are equal`() {
        val expected = PixelLine.create(4)
            .flatMap { it.changePixel(1, Pixel.X) }

        val actual = PixelLine.create(4)
            .flatMap { it.changePixel(1, Pixel.X) }

        actual shouldBeRight actual
    }

    @Test
    fun `two different pixel lines are NOT equal`() {
        val expected = PixelLine.create(4)
            .flatMap { it.changePixel(1, Pixel.X) }

        val actual = PixelLine.create(4)
            .flatMap { it.changePixel(2, Pixel.X) }

        actual shouldBeRight actual
    }

    @Test
    fun `two empty lines the same length will be merged to one empty line`() {
        tupled(
            PixelLine.create(4),
            PixelLine.create(4)
        ).flatMap {
            it.a.mergeOnTheSurface(it.b)
        } shouldBe PixelLine.create(4)
    }

    @Test
    fun `filled line might me merged on empty line`() {
        val filledLine = PixelLine.create(4).flatMap { it.changePixel(1, Pixel.X) }
        val emptyLine = PixelLine.create(4)

        tupled(
            emptyLine,
            filledLine
        ).flatMap {
            it.a.mergeOnTheSurface(it.b)
        } shouldBe filledLine
    }

    @Test
    fun `2 pixel lines with different length can not be merged`() {
        tupled(
            PixelLine.create(4),
            PixelLine.create(5)
        ).flatMap {
            it.a.mergeOnTheSurface(it.b)
        }.shouldBeLeftOfType<LinesCanNotBeMerged>()
    }
}