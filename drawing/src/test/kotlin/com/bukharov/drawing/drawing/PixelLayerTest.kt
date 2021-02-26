package com.bukharov.drawing.drawing

import com.bukharov.drawing.geometry.Point
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import org.junit.jupiter.api.Test

internal class PixelLayerTest {

    @Test
    fun `pixelLayer can not be created with not positive height`() {
        PixelLayer.create(width = 4, height = 0) shouldBeLeft WrongWidthAndHeight
    }

    @Test
    fun `pixelLayer can not be created with not positive width`() {
        PixelLayer.create(width = 0, height = 3) shouldBeLeft WrongWidthAndHeight
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
            layer -> layer.get(Point(x = 25, y = 25)) shouldBeLeft PixelDoesNotExist
        }
    }

    @Test
    fun `pixel is NOT be retrievable if it is out of borders - negative`() {
        PixelLayer.create(width = 5, height = 5) shouldBeRight {
            layer -> layer.get(Point(x = 25, y = -25)) shouldBeLeft PixelDoesNotExist
        }
    }

    @Test
    fun `pixel might be chnaged if it exists`() {
        val layer = PixelLayer.create(width = 5, height = 5)
        val pixelPoint = Point(x = 3, y = 3)
        layer shouldBeRight {
            it.change(pixelPoint, Pixel.X)
        }

        layer.map {
            it.get(pixelPoint) shouldBeRight { Pixel.X }
        }
    }
    
}