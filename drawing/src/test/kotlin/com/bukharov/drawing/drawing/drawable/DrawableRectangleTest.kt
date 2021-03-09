package com.bukharov.drawing.drawing.drawable

import com.bukharov.drawing.drawing.pixel.print
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.Rectangle
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

        println(actual.asStrings().print())
    }
}