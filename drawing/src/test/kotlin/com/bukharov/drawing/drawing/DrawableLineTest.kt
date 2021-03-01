package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Line
import com.bukharov.drawing.geometry.Point
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DrawableLineTest {

    @Test
    fun `horizontal line rasterized properly`() {
        val expectedPixelLayer = PixelLayer
            .create(4, 2)
            .change(Point(1, 1), Pixel.X)
            .change(Point(2, 1), Pixel.X)
            .change(Point(3, 1), Pixel.X)

        val actualPixelLayer = Line
        .create(Point(1, 1), Point(3, 1))
        .let { DrawableLine(it) }.rasterize()

        actualPixelLayer shouldBe expectedPixelLayer
    }

    @Test
    fun `vertical line rasterized properly`() {
        val expectedPixelLayer = PixelLayer
            .create(2, 4)
            .change(Point(1, 1), Pixel.X)
            .change(Point(1, 2), Pixel.X)
            .change(Point(1, 3), Pixel.X)

        val actualPixelLayer = Line
        .create(Point(1, 1), Point(1, 3))
        .let { DrawableLine(it) }.rasterize()

        actualPixelLayer shouldBe expectedPixelLayer
    }
}