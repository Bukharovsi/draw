package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Line
import com.bukharov.drawing.geometry.Point
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DrawableCanvasTest {

    @Test
    fun `empty canvas will be rasterized to empty pixel layer`() {
        val fiveXfive = Dimensions(5, 5)
        val canvas = DrawableCanvas(fiveXfive)
        val actualPixelLayer = canvas.rasterize()

        val emptyPixelLayer = PixelLayer.create(fiveXfive)

        actualPixelLayer shouldBe emptyPixelLayer
    }

    @Test
    fun `shape on pictures must be rasterized`() {
        val fiveXfive = Dimensions(5, 5)
        val canvas = DrawableCanvas(fiveXfive)
        canvas.put(Line.create(Point(1, 1), Point(1, 3)))
        val actualPixelLayer = canvas.rasterize()

        val expectedPixelLayer = PixelLayer.create(fiveXfive)
        expectedPixelLayer[Point(1, 1)] = Pixel.X
        expectedPixelLayer[Point(1, 2)] = Pixel.X
        expectedPixelLayer[Point(1, 3)] = Pixel.X

        actualPixelLayer shouldBe expectedPixelLayer
    }

    @Test
    fun `shape on pictures must be printable`() {
        val fiveXfive = Dimensions(5, 5)
        val canvas = DrawableCanvas(fiveXfive)
        canvas.put(Line.create(Point(0, 0), Point(0, 4)))
        canvas.put(Line.create(Point(0, 4), Point(4, 4)))
        val actualPicture = canvas.print()

        val expectedPicture =
            """
            x    
            x    
            x    
            x    
            xxxxx
            
            """.trimIndent()

        actualPicture shouldBe expectedPicture
    }
}