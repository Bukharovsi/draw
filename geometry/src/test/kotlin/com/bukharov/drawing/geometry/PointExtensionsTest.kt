package com.bukharov.drawing.geometry

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class PointExtensionsTest {

    @Test
    fun `calculate max x point`() {
        maxXof(Point.zero, Point(3, 4)) shouldBe 3
    }

    @Test
    fun `calculate max y point`() {
        maxYof(Point.zero, Point(3, 4)) shouldBe 4
    }

    @Test
    fun `calculate upper right Point`() {
        upperRightOfPoints(Point(3, 4), Point(1, 10)) shouldBe (Point(3, 10))
    }

    @Test
    fun `calculate lowest left Point`() {
        lowestLeftOfPoints(Point(3, 4), Point(1, 10)) shouldBe (Point(1, 4))
    }

    @Test
    fun `point inc x`() {
        Point.zero.incX() shouldBe Point(1, 0)
    }

    @Test
    fun `point inc y`() {
        Point.zero.incY() shouldBe Point(0, 1)
    }

    @Test
    fun `point dec x`() {
        Point.zero.decX() shouldBe Point(-1, 0)
    }

    @Test
    fun `point dec y`() {
        Point.zero.decY() shouldBe Point(0, -1)
    }
}