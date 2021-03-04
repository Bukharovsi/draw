package com.bukharov.drawing.drawing.filling

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test

internal class FloodFillTest {

    @Test
    fun `should fill empty canvas entierly`() {
        val origin = PixelLayer(Dimensions(5, 5))
        val fillWith = Pixel.O
        val filled = FloodFill(origin).fill(Point(3, 3), fillWith)

        filled.print().shouldContain(Regex("^${fillWith.print()}+$", RegexOption.MULTILINE))
    }

    @Test
    fun `should fill canvas but only needed area`() {
        val origin = PixelLayer(Dimensions(5, 5))
        origin[Point(3, 4)] = Pixel.X
        origin[Point(3, 3)] = Pixel.X
        origin[Point(4, 3)] = Pixel.X

        val filled = FloodFill(origin).fill(Point.zero, Pixel.O)

        filled.print().shouldContain(Regex("^${Pixel.O.print()}+$", RegexOption.MULTILINE))
        filled[Point(4, 4)] shouldBe Pixel.Empty
    }

    @Test
    fun `should fill figure if target point is figure`() {
        val origin = PixelLayer(Dimensions(5, 5))
        origin[Point(3, 4)] = Pixel.X
        origin[Point(3, 3)] = Pixel.X
        origin[Point(4, 3)] = Pixel.X

        val filled = FloodFill(origin).fill(Point(3, 4), Pixel.O)

        filled[Point(3, 4)] shouldBe Pixel.O
        filled[Point(3, 3)] shouldBe Pixel.O
        filled[Point(4, 3)] shouldBe Pixel.O
        filled[Point.zero] shouldBe Pixel.Empty
        filled[Point(4, 4)] shouldBe Pixel.Empty
    }

    @Test
    fun `should throw exeption if target point is out of canvas`() {
        val origin = PixelLayer(Dimensions(5, 5))
        shouldThrow<CanNotFillPointItIsOutOfCanvas> {
            FloodFill(origin).fill(Point(100, 4), Pixel.O)
        }
    }
}