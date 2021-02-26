package com.bukharov.drawing.drawing

import arrow.core.flatMap
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

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
            pixelLine[3] shouldBeLeft PixelDoesNotExist
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
            .shouldBeLeft(PixelDoesNotExist)
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
}