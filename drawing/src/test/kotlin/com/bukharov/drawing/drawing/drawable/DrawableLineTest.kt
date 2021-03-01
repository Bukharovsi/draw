package com.bukharov.drawing.drawing.drawable

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Line
import com.bukharov.drawing.geometry.Point
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DrawableLineTest {

    @Test
    fun `vertical line will be rasterized and print to a line`() {
            val image = Line.create(Point(0, 0), Point(0, 1))
                .let { DrawableLine(it) }.rasterize().print()

        image shouldBe "x\nx\n"
    }

    @Test
    fun `rasterized vertical line should have proper size`() {
        Line.create(Point(0, 0), Point(0, 1))
            .let { DrawableLine(it) }.rasterize()
            .dimensions shouldBe Dimensions(1, 2)
    }

    @Test
    fun `rasterized horizontal line should have proper size`() {
        Line.create(Point(0, 0), Point(1, 0))
            .let { DrawableLine(it) }.rasterize()
            .dimensions shouldBe Dimensions(2, 1)
    }

    @Test
    fun `horizontal line rasterized properly`() {
        val expectedPixelLayer = PixelLayer
            .create(4, 2)
            .change(Point(1, 1), Pixel.X)
            .change(Point(2, 1), Pixel.X)
            .change(Point(3, 1), Pixel.X)

        val actualPixelLayer =
            Line.create(Point(1, 1), Point(3, 1))
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