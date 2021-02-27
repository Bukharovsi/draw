package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelDoesNotExist
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.DimensionMustBePositive
import com.bukharov.drawing.geometry.Point
import io.kotest.assertions.arrow.either.shouldBeLeftOfType
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class PixelLayerTest {

    @Test
    fun `pixelLayer can not be created with not positive height`() {
        PixelLayer.create(width = 4, height = 0).shouldBeLeftOfType<DimensionMustBePositive>()
    }

    @Test
    fun `pixelLayer can not be created with not positive width`() {
        PixelLayer.create(width = 0, height = 3).shouldBeLeftOfType<DimensionMustBePositive>()
    }

    @Test
    fun `pixel might be retrievable if it exists`() {
        PixelLayer.create(width = 5, height = 5) shouldBeRight {
            layer -> layer.get(Point(x = 2, y = 2)) shouldBeRight { Pixel.Empty }
        }
    }

    @Test
    fun `pixel is NOT be retrievable if it is out of borders`() {
        PixelLayer.create(width = 5, height = 5) shouldBeRight {
            layer -> layer.get(Point(x = 25, y = 25))
            .shouldBeLeftOfType<PixelDoesNotExist<Point>>()
        }
    }

    @Test
    fun `pixel is NOT be retrievable if it is out of borders - negative`() {
        PixelLayer.create(width = 5, height = 5) shouldBeRight {
            layer -> layer.get(Point(x = 25, y = -25))
            .shouldBeLeftOfType<PixelDoesNotExist<Point>>()
        }
    }

    @Test
    fun `pixel might be changed if it exists`() {
        val layer = PixelLayer.create(width = 5, height = 5)
        val pixelPointInsideBorders = Point(x = 3, y = 3)
        layer shouldBeRight {
            it.change(pixelPointInsideBorders, Pixel.X).shouldBeRight()
            it.get(pixelPointInsideBorders) shouldBeRight { Pixel.X }
        }
    }

    @Test
    fun `pixel should not be changed if it does NOT exist`() {
        val layer = PixelLayer.create(width = 5, height = 5)
        val pixelPointOutsideBorders = Point(x = 23, y = 23)
        layer shouldBeRight {
            it.change(pixelPointOutsideBorders, Pixel.X)
                .shouldBeLeftOfType<PixelDoesNotExist<Point>>()
        }
    }

    @Test
    fun `2 empty pixel Layers are identical`() {
        PixelLayer.create(width = 5, height = 5) shouldBe PixelLayer.create(width = 5, height = 5)
    }

    @Test
    fun test() {
        val byteStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteStream)

        val layer = PixelLayer.create(3, 3)
        layer.map { pixelLayer ->
            pixelLayer.change(Point.zero, Pixel.X)
            pixelLayer.change(Point(0, 1), Pixel.O)
            pixelLayer.change(Point(0, 2), Pixel.O)
            pixelLayer.change(Point(1, 0), Pixel.O)
            pixelLayer.change(Point(1, 1), Pixel.X)
            pixelLayer.change(Point(1, 2), Pixel.O)
            pixelLayer.change(Point(2, 0), Pixel.O)
            pixelLayer.change(Point(2, 1), Pixel.O)
            pixelLayer.change(Point(2, 2), Pixel.X)
            pixelLayer.drawTo(printStream)
        }

        val printed = String(byteStream.toByteArray())
        val expected = """
           xoo
           oxo
           oox
           
        """.trimIndent()

        printed shouldBe expected
    }
}