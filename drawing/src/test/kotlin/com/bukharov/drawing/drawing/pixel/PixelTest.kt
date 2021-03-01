package com.bukharov.drawing.drawing.pixel

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class PixelTest {

    @Test
    fun `2 different pixels are not equal`() {
        Pixel.X shouldNotBe Pixel.FiledPixel('o')
    }

    @Test
    fun `2 identical pixels are the equal`() {
        Pixel.X shouldBe Pixel.FiledPixel('x')
    }

    @Test
    fun `pixel prints its color`() {
        Pixel.X.print() shouldBe 'x'
    }

    @Test
    fun `empty pixel prints BLANK`() {
        Pixel.Empty.print() shouldBe ' '
    }

    @Test
    fun `2 empty pixels merged - 1 empty pixel`() {
        Pixel.Empty.mergeAtop(Pixel.Empty) shouldBe Pixel.Empty
    }

    @Test
    fun `empty pixels merged on top of filled - filled`() {
        Pixel.Empty.mergeAtop(Pixel.X) shouldBe Pixel.X
    }

    @Test
    fun `filed pixels merged on top of empty - filled`() {
        Pixel.X.mergeAtop(Pixel.Empty) shouldBe Pixel.X
    }

    @Test
    fun `2 filled pixels merged on top - above filled`() {
        Pixel.X.mergeAtop(Pixel.O) shouldBe Pixel.O
    }
}