package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.pixel.LineLengthShouldBePositiveValue
import com.bukharov.drawing.drawing.pixel.LinesCanNotBeMerged
import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelDoesNotExist
import com.bukharov.drawing.drawing.pixel.PixelLine
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.junit.jupiter.api.Test

internal class PixelLineTest {

    @Test
    fun `new created pixel line should have mnot more than length pixels`() {
        PixelLine.create(2) should { line ->
            line.iterator()
                .also { it.next() }
                .also { it.next() }
                .also { it.hasNext() shouldBe false }
        }
    }

    @Test
    fun `new created pixel line has all empty pixels`() {
        PixelLine.create(5)
            .should {
                it.forEach { pixel -> pixel shouldBe Pixel.Empty }
            }
    }

    @Test
    fun `pixelLine can not be created with 0 length`() {
        shouldThrow<LineLengthShouldBePositiveValue> { PixelLine.create(0) }
    }

    @Test
    fun `pixelLine can not be created with negative length`() {
        shouldThrow<LineLengthShouldBePositiveValue> { PixelLine.create(-1) }
    }

    @Test
    fun `pixel can not be retrievable by index if id does not exist`() {
        shouldThrow<PixelDoesNotExist> {
            PixelLine.create(length = 3)[3]
        }
    }

    @Test
    fun `pixel can be retrievable by index if it exists`() {
        PixelLine.create(length = 3)[2] shouldBe Pixel.Empty
    }

    @Test
    fun `pixel with negative index does not exist`() {
        PixelLine.create(5).has(-1) shouldBe false
    }

    @Test
    fun `pixel with index out of range of line does not exist`() {
        PixelLine.create(5).has(-1) shouldBe false
    }

    @Test
    fun `pixel with index inside range of line exist`() {
        PixelLine.create(5).has(3) shouldBe true
    }

    @Test
    fun `when pixel has not been changed then Error`() {
        shouldThrow<PixelDoesNotExist> {
            PixelLine.create(5)
                .changePixel(6, Pixel.X)
        }
    }

    @Test
    fun `when pixel is changed successfully then it will be retrievable`() {
        val newPixel = Pixel.X
        PixelLine
            .create(5)
            .changePixel(1, newPixel)[1] shouldBe newPixel
    }

    @Test
    fun `empty pixel line should be printed as all blanks`() {
        val byteStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteStream)
        PixelLine.create(5).drawTo(printStream)

        val printedStream = String(byteStream.toByteArray())
        val expectedString = "     " // 5 chars
        printedStream shouldBe expectedString
    }

    @Test
    fun `not empty pixel line should be printed right`() {
        val byteStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteStream)
        PixelLine.create(5)
            .changePixel(1, Pixel.X)
            .changePixel(2, Pixel.X)
            .drawTo(printStream)

        val printedStream = String(byteStream.toByteArray())
        val expectedString = " xx  " // 5 chars
        printedStream shouldBe expectedString
    }

    @Test
    fun `two empty pixel lines are equal`() {
        PixelLine.create(4) shouldBe PixelLine(4)
    }

    @Test
    fun `two identical pixel lines are equal`() {
        val expected = PixelLine
            .create(4).changePixel(1, Pixel.X)

        val actual = PixelLine
            .create(4).changePixel(1, Pixel.X)

        actual shouldBe expected
    }

    @Test
    fun `two different pixel lines are NOT equal`() {
        val expected = PixelLine.create(4)
            .changePixel(1, Pixel.X)

        val actual = PixelLine.create(4)
            .changePixel(2, Pixel.X)

        actual shouldNotBe expected
    }

    @Test
    fun `two empty lines the same length will be merged to one empty line`() {
            PixelLine
                .create(4)
                .mergeAtop(PixelLine.create(4))
                .shouldBe(PixelLine.create(4))
    }

    @Test
    fun `filled line might me merged on empty line`() {
        val filledLine = PixelLine.create(4).changePixel(1, Pixel.X)
        val emptyLine = PixelLine.create(4)

        emptyLine.mergeAtop(filledLine) shouldBe filledLine
    }

    @Test
    fun `2 pixel lines with different length can not be merged`() {
        shouldThrow<LinesCanNotBeMerged> {
            PixelLine
                .create(4)
                .mergeAtop(PixelLine.create(5))
        }
    }
}