package com.bukharov.drawing.geometry

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class LineTest {

    @Test
    fun `if line is vertical - it will be created`() {
        Line.create(
            Point.zero,
            Point(0, 3)
        ) shouldBe Line(
            Point.zero,
            Point(0, 3)
        )
    }

    @Test
    fun `if line is horizontal - it will be created`() {
        Line.create(
            Point.zero,
            Point(3, 0)
        ) shouldBe Line(
            Point.zero,
            Point(3, 0)
        )
    }

    @Test
    fun `if line is just point - it will not be created`() {
        shouldThrow<LineShouldNotBePoint> { Line.create(Point.zero, Point.zero) }
    }

    @Test
    fun `if line is not horizontal or vertical it should not be created`() {
        shouldThrow<LineShouldBeVerticalOrHorizontal> {
            Line.create(
                Point.zero,
                Point(5, 5)
            )
        }
    }

    @Test
    fun `lines should be equal if coordinates are the same`() {
        val a = Point.zero
        val b = Point(0, 5)
        Line.create(a, b) shouldBe Line.create(a, b)
    }

    @Test
    fun `lines should NOT be equal if coordinates are different`() {
        val a = Point.zero
        val b = Point(0, 5)
        Line.create(a, b) shouldNotBe Line.create(a, Point(0, 6))
    }

    @Test
    fun `lines should be equal if coordinates are swapped`() {
        val p1 = Point.zero
        val p2 = Point(0, 2)
        Line.create(p1, p2) shouldBe Line(p2, p1)
    }

    @Test
    fun `line returns correct upper right corner if p2 is actual upper right corner`() {
        val p1 = Point(1, 3)
        val p2 = Point(1, 5)
        Line.create(p1, p2).upperRightCorner shouldBe p2
    }

    @Test
    fun `line returns correct upper right corner if p1 is actual upper right corner`() {
        val p1 = Point(10, 20)
        val p2 = Point(10, 5)
        Line.create(p1, p2).upperRightCorner shouldBe p1
    }

    @Test
    fun `line returns correct lowest left corner if p1 is actual lowest left corner`() {
        val p1 = Point(1, 3)
        val p2 = Point(4, 3)
        Line.create(p1, p2).lowerLeftCorner shouldBe p1
    }
}