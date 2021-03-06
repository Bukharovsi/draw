package com.bukharov.drawing.drawing.pixel

import com.bukharov.drawing.geometry.DimensionMustBePositive
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class PixelLayerTest {

    @Test
    fun `pixelLayer can not be created with not positive height`() {
        shouldThrow<DimensionMustBePositive> { PixelLayer(Dimensions(width = 4, height = 0)) }
    }

    @Test
    fun `pixelLayer can not be created with not positive width`() {
        shouldThrow<DimensionMustBePositive> { PixelLayer(Dimensions(width = 0, height = 3)) }
    }

    @Test
    fun `pixel might be retrievable if it exists`() {
        PixelLayer(Dimensions(width = 5, height = 5))[Point(x = 2, y = 2)] shouldBe Pixel.Empty
    }

    @ParameterizedTest
    @CsvSource(
        "25, 25",
        "25, -25",
        "-25, 25"
    )
    fun `pixel is NOT be retrievable if it is out of borders`(x: Int, y: Int) {
        shouldThrow<LayerPixelDoesNotExist> {
            PixelLayer(Dimensions(width = 5, height = 5))[Point(x = x, y = y)]
        }
    }

    @Test
    fun `pixel might be changed if it exists`() {
        val pixelPointInsideBorders = Point(x = 3, y = 3)

        PixelLayer(Dimensions(width = 5, height = 5))
            .also { it[pixelPointInsideBorders] = Pixel.X }
            .get(pixelPointInsideBorders) shouldBe Pixel.X
    }

    @Test
    fun `pixel should not be changed if it does NOT exist`() {
        shouldThrow<LayerPixelDoesNotExist> {
            PixelLayer(Dimensions(width = 5, height = 5))
                .set(Point(x = 23, y = 23), Pixel.X)
        }
    }

    @Test
    fun `2 empty pixel Layers are identical`() {
        PixelLayer(Dimensions(width = 5, height = 5))
            .shouldBe(PixelLayer(Dimensions(width = 5, height = 5)))
    }

    @Test
    fun `filled pixels should be drawable`() {
        val printed = PixelLayer(Dimensions(3, 3))
            .also {
                it[Point.zero] = Pixel.X
                it[Point(0, 1)] = Pixel.O
                it[Point(0, 2)] = Pixel.O
                it[Point(1, 0)] = Pixel.O
                it[Point(1, 1)] = Pixel.X
                it[Point(1, 2)] = Pixel.O
                it[Point(2, 0)] = Pixel.O
                it[Point(2, 1)] = Pixel.O
                it[Point(2, 2)] = Pixel.X
            }.asStrings()

        val expected = listOf(
            "xoo",
            "oxo",
            "oox"
        )

        printed shouldBe expected
    }

    @Test
    fun `2 layers the same dimension should be merged`() {
        val background = PixelLayer(Dimensions(3, 3), Pixel.O)
        val layer1 = PixelLayer(Dimensions(3, 3))
            .also {
                it[Point(0, 0)] = Pixel.X
                it[Point(1, 1)] = Pixel.X
                it[Point(2, 2)] = Pixel.X
            }

        val merged = background.mergeAtop(layer1).asStrings()

        val expected = listOf(
            "xoo",
            "oxo",
            "oox"
        )

        merged shouldBe expected
    }

    @Test
    fun `2 empty layers merged - empty layer`() {
        val background = PixelLayer(Dimensions(3, 3))
        val layer1 = PixelLayer(Dimensions(3, 3))

        val merged = background.mergeAtop(layer1)

        merged shouldBe PixelLayer(Dimensions(3, 3))
    }

    @Test
    fun `a big layer can not be merged on top of a small one`() {
        shouldThrow<LayersHaveDifferentSize> {
            PixelLayer(Dimensions(3, 3))
                .mergeAtop(PixelLayer(Dimensions(10, 10)))
        }
    }

    @Test
    fun `a small layer can be merged on top of a large one`() {
        PixelLayer(Dimensions(3, 3))
            .mergeAtop(PixelLayer(Dimensions(2, 2)))
            .shouldBe(PixelLayer(Dimensions(3, 3)))
    }
}