package com.bukharov.drawing.drawing.drawable

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Field
import com.bukharov.drawing.geometry.Line
import com.bukharov.drawing.geometry.Point
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DrawableFieldTest {

    @Test
    fun `fully filled canvas might be rasterized`() {
        val filledCanvas = Field(rightUpperCorner = Point(1, 1))
            .put(Line.create(Point.zero, Point(0, 1)))
            .put(Line.create(Point.zero, Point(1, 0)))
            .put(Line.create(Point(1, 1), Point(1, 0)))

        val expected = PixelLayer(Dimensions(2, 2))
        expected[Point(0, 0)] = Pixel.X
        expected[Point(0, 1)] = Pixel.X
        expected[Point(1, 0)] = Pixel.X
        expected[Point(1, 1)] = Pixel.X

        DrawableField(field = filledCanvas).rasterize() shouldBe expected
    }

    @Test
    fun `canvas might be rasterized`() {
        val twoLines = Field(rightUpperCorner = Point(5, 5))
            .put(Line.create(Point.zero, Point(0, 4)))
            .put(Line.create(Point.zero, Point(4, 0)))

        val imageOf2Lines = DrawableField(twoLines)
            .rasterize()

        val expected = PixelLayer.create(Dimensions(6, 6))
        expected[Point(0, 0)] = Pixel.X

        expected[Point(0, 1)] = Pixel.X
        expected[Point(0, 2)] = Pixel.X
        expected[Point(0, 3)] = Pixel.X
        expected[Point(0, 4)] = Pixel.X

        expected[Point(1, 0)] = Pixel.X
        expected[Point(2, 0)] = Pixel.X
        expected[Point(3, 0)] = Pixel.X
        expected[Point(4, 0)] = Pixel.X

        println("expected:")
        println(expected.print())

        println("actual:")
        println(imageOf2Lines.print())

        imageOf2Lines shouldBe expected
    }
}