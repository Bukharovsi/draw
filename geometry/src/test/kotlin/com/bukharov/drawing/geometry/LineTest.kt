package com.bukharov.drawing.geometry

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.assertions.arrow.either.shouldNotBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class LineTest {

    @Test
    fun `if line is vertical - it will be created`() {
        Line.create(
            Point.zero,
            Point(0, 3)
        ) shouldBeRight Line(
            Point.zero,
            Point(0, 3)
        )
    }

    @Test
    fun `if line is horizontal - it will be created`() {
        Line.create(
            Point.zero,
            Point(3, 0)
        ) shouldBeRight Line(
            Point.zero,
            Point(3, 0)
        )
    }

    @Test
    fun `if line is just point - it will not be created`() {
        Line.create(
            Point.zero,
            Point.zero
        ) shouldBeLeft LineShouldNotBePoint
    }

    @Test
    fun `if line is not horizontal or vertical it should not be created`() {
        Line.create(
            Point.zero,
            Point(5, 5)
        ) shouldBeLeft LineShouldBeVerticalOrHorizontal
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
        Line.create(a, b) shouldNotBeRight Line.create(a, Point(0, 6))
    }

    @Test
    fun `lines should be equal if coordinates are swapped`() {
        val p1 = Point.zero
        val p2 = Point(0, 2)
        Line.create(p1, p2) shouldBeRight Line(p2, p1)
    }

    @Test
    fun `line returns correct upper right corner if p2 is actual upper right corner`() {
        val p1 = Point(1, 3)
        val p2 = Point(1, 5)
        Line.create(p1, p2)
            .shouldBeRight {
                it.upperRightCorner() shouldBe p2
            }
    }

    @Test
    fun `line returns correct upper right corner if p1 is actual upper right corner`() {
        val p1 = Point(10, 20)
        val p2 = Point(10, 5)
        Line.create(p1, p2)
            .shouldBeRight {
                it.upperRightCorner() shouldBe p1
            }
    }

    @Test
    fun `line returns correct lowest left corner if p1 is actual lowest left corner`() {
        val p1 = Point(1, 3)
        val p2 = Point(4, 3)
        Line.create(p1, p2)
            .shouldBeRight {
                it.downLeftCorner() shouldBe p1
            }
    }
}