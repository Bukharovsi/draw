package com.bukharov.drawing.drawing.pixel

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class PixelRowTest {

    @Test
    fun `new created pixel line should have not more than length pixels`() {
        PixelRow.create(2) should { line ->
            line.iterator()
                .also { it.next() }
                .also { it.next() }
                .also { it.hasNext() shouldBe false }
        }
    }

    @Test
    fun `new created pixel line has all empty pixels`() {
        PixelRow.create(5)
            .should {
                it.forEach { pixel -> pixel shouldBe Pixel.Empty }
            }
    }

    @Test
    fun `pixelLine can not be created with 0 length`() {
        shouldThrow<LineLengthShouldBePositiveValue> { PixelRow.create(0) }
    }

    @Test
    fun `pixelLine can not be created with negative length`() {
        shouldThrow<LineLengthShouldBePositiveValue> { PixelRow.create(-1) }
    }

    @Test
    fun `pixel can not be retrievable by index if id does not exist`() {
        shouldThrow<PixelDoesNotExist> {
            PixelRow.create(length = 3)[3]
        }
    }

    @Test
    fun `pixel can be retrievable by index if it exists`() {
        PixelRow.create(length = 3)[2] shouldBe Pixel.Empty
    }

    @Test
    fun `pixel with negative index does not exist`() {
        PixelRow.create(5).has(-1) shouldBe false
    }

    @Test
    fun `pixel with index out of range of line does not exist`() {
        PixelRow.create(5).has(-1) shouldBe false
    }

    @Test
    fun `pixel with index inside range of line exist`() {
        PixelRow.create(5).has(3) shouldBe true
    }

    @Test
    fun `when pixel has not been changed then Error`() {
        shouldThrow<PixelDoesNotExist> {
            PixelRow.create(5)
                .set(6, Pixel.X)
        }
    }

    @Test
    fun `when pixel is changed successfully then it will be retrievable`() {
        val newPixel = Pixel.X
        PixelRow
            .create(5)
            .apply { this[1] = newPixel }
            .get(1)
            .shouldBe(newPixel)
    }

    @Test
    fun `empty pixel line should be printed as all blanks`() {
        val printedStream = PixelRow.create(5).print()
        val expectedString = "     " // 5 chars
        printedStream shouldBe expectedString
    }

    @Test
    fun `not empty pixel line should be printed right`() {
        val printedStream = PixelRow.create(5)
            .apply { this[1] = Pixel.X }
            .apply { this[2] = Pixel.X }
            .print()

        val expectedString = " xx  " // 5 chars
        printedStream shouldBe expectedString
    }

    @Test
    fun `two empty pixel lines are equal`() {
        PixelRow.create(4) shouldBe PixelRow(4)
    }

    @Test
    fun `two identical pixel lines are equal`() {
        val expected = PixelRow
            .create(4).set(1, Pixel.X)

        val actual = PixelRow
            .create(4).set(1, Pixel.X)

        actual shouldBe expected
    }

    @Test
    fun `two different pixel lines are NOT equal`() {
        val expected = PixelRow.create(4)
            .apply { this[1] = Pixel.X }

        val actual = PixelRow.create(4)
            .apply { this[2] = Pixel.X }

        actual shouldNotBe expected
    }

    @Test
    fun `two empty lines the same length will be merged to one empty line`() {
            PixelRow
                .create(4)
                .mergeAtop(PixelRow.create(4))
                .shouldBe(PixelRow.create(4))
    }

    @Test
    fun `filled line might me merged on empty line`() {
        val filledLine = PixelRow.create(4)
            .apply { this[1] = Pixel.X }
        val emptyLine = PixelRow.create(4)

        emptyLine.mergeAtop(filledLine) shouldBe filledLine
    }

    @Test
    fun `long line can not be merged on a short one`() {
        shouldThrow<LinesCanNotBeMerged> {
            PixelRow
                .create(4)
                .mergeAtop(PixelRow.create(5))
        }
    }

    @Test
    fun `short empty line might be merged on a long one`() {
            PixelRow
                .create(3)
                .mergeAtop(PixelRow.create(2))
                .shouldBe(PixelRow(3))
    }

    @Test
    fun `two partly filled lines merged into one partly filled`() {
        val line1 = PixelRow.create(5)
            .apply { this[0] = Pixel.X }
            .apply { this[1] = Pixel.X }

        val line2 = PixelRow.create(5)
            .apply { this[3] = Pixel.X }
            .apply { this[4] = Pixel.X }

        val expected = PixelRow.create(5)
            .apply { this[0] = Pixel.X }
            .apply { this[1] = Pixel.X }
            .apply { this[3] = Pixel.X }
            .apply { this[4] = Pixel.X }

        line1.mergeAtop(line2) shouldBe expected
    }

    @Test
    fun `cloned PixelLine equal to original one`() {
        val origin = PixelRow.create(3)
            .apply { this[0] = Pixel.X }
        origin.clone() shouldBe origin
    }
}