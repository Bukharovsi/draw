package com.bukharov.drawing.drawing

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
}