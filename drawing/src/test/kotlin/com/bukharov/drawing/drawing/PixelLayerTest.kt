package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.pixel.LayerPixelDoesNotExist
import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.DimensionMustBePositive
import com.bukharov.drawing.geometry.Point
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class PixelLayerTest {

    @Test
    fun `pixelLayer can not be created with not positive height`() {
        shouldThrow<DimensionMustBePositive> { PixelLayer.create(width = 4, height = 0) }
    }

    @Test
    fun `pixelLayer can not be created with not positive width`() {
        shouldThrow<DimensionMustBePositive> { PixelLayer.create(width = 0, height = 3) }
    }

    @Test
    fun `pixel might be retrievable if it exists`() {
        PixelLayer.create(width = 5, height = 5)
            .get(Point(x = 2, y = 2)) shouldBe Pixel.Empty
    }

    @ParameterizedTest
    @CsvSource(
        "25, 25",
        "25, -25",
        "-25, 25"
    )
    fun `pixel is NOT be retrievable if it is out of borders`(x: Int, y: Int) {
        shouldThrow<LayerPixelDoesNotExist> {
            PixelLayer.create(width = 5, height = 5)
                .get(Point(x = x, y = y))
        }
    }

    @Test
    fun `pixel might be changed if it exists`() {
        val pixelPointInsideBorders = Point(x = 3, y = 3)

        PixelLayer.create(width = 5, height = 5)
            .change(pixelPointInsideBorders, Pixel.X)
            .get(pixelPointInsideBorders) shouldBe Pixel.X
    }

    @Test
    fun `pixel should not be changed if it does NOT exist`() {
        shouldThrow<LayerPixelDoesNotExist> {
            PixelLayer
                .create(width = 5, height = 5)
                .change(Point(x = 23, y = 23), Pixel.X)
        }
    }

    @Test
    fun `2 empty pixel Layers are identical`() {
        PixelLayer.create(width = 5, height = 5) shouldBe PixelLayer.create(width = 5, height = 5)
    }

    @Test
    fun `filled pixels should be drawable`() {
        val byteStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteStream)

        PixelLayer.create(3, 3)
            .change(Point.zero, Pixel.X)
            .change(Point(0, 1), Pixel.O)
            .change(Point(0, 2), Pixel.O)
            .change(Point(1, 0), Pixel.O)
            .change(Point(1, 1), Pixel.X)
            .change(Point(1, 2), Pixel.O)
            .change(Point(2, 0), Pixel.O)
            .change(Point(2, 1), Pixel.O)
            .change(Point(2, 2), Pixel.X)
            .drawTo(printStream)

        val printed = String(byteStream.toByteArray())
        val expected = """
           xoo
           oxo
           oox
           
        """.trimIndent()

        printed shouldBe expected
    }
}