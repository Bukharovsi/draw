package com.bukharov.drawing.drawing.drawable

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.Rectangle
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DrawableRectangleTest {

    @Test
    fun `rectangle - rasterized`() {

        val actual = DrawableRectangle(
            Rectangle(
                upperLeftCorner = Point(0, 3),
                lowerRightCorner = Point(3, 0)
            )
        ).rasterize()

        val expected = PixelLayer(Dimensions(4, 4))
            .apply { this[Point(0, 0)] = Pixel.X }
            .apply { this[Point(0, 1)] = Pixel.X }
            .apply { this[Point(0, 2)] = Pixel.X }
            .apply { this[Point(0, 3)] = Pixel.X }
            .apply { this[Point(1, 3)] = Pixel.X }
            .apply { this[Point(2, 3)] = Pixel.X }
            .apply { this[Point(3, 3)] = Pixel.X }
            .apply { this[Point(3, 2)] = Pixel.X }
            .apply { this[Point(3, 1)] = Pixel.X }
            .apply { this[Point(3, 0)] = Pixel.X }
            .apply { this[Point(1, 0)] = Pixel.X }
            .apply { this[Point(2, 0)] = Pixel.X }

        actual shouldBe expected
    }
}