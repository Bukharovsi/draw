package com.bukharov.drawing

import arrow.core.extensions.either.apply.tupled
import arrow.core.flatMap
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class CanvasTest {

    @Test
    fun `canvas with positive values width and height might be created`() {
        Canvas.create(
            rightUpperCorner = Point(6, 6)
        ) shouldBeRight Canvas(
            rightUpperCorner = Point(6, 6)
        )
    }

    @Test
    fun `two canvas with the same width and height are equal`() {
        Canvas(
            rightUpperCorner = Point(6, 6)
        ) shouldBe Canvas(
            rightUpperCorner = Point(6, 6)
        )
    }

    @Test
    fun `two canvas with the different width and height are NOT equal`() {
        Canvas(
            rightUpperCorner = Point(6, 6)
        ) shouldNotBe  Canvas(
            rightUpperCorner = Point(6, 7)
        )
    }

    @Test
    fun `canvas might not have width 0`() {
        Canvas.create(
            rightUpperCorner = Point(0, 6)
        ) shouldBeLeft WrongCanvasSize
    }

    @Test
    fun `canvas might not have height 0`() {
        Canvas.create(
            rightUpperCorner = Point(6, 0)
        ) shouldBeLeft WrongCanvasSize
    }

    @Test
    fun `a shape might be placed on canvas if it is within canvas`() {
        tupled(
            Canvas.create(Point(9, 9)),
            Line.create(Point.zero, Point(0,3))
        ).flatMap {
            it.a.put(it.b)
        } shouldBeRight {
            it.shapes().size shouldBe 1
            it.shapes().contains(Line(Point.zero, Point(0,3)))
        }
    }

    @Test
    fun `a shape might NOT be placed on canvas if it does not fit canvas`() {
        tupled(
            Canvas.create(Point(9, 9)),
            Line.create(Point.zero, Point(0,14))
        ).flatMap {
            it.a.put(it.b)
        } shouldBeLeft ShapeCanNotBePlacedToCanvas
    }

    @Test
    fun `a shape might not be placed on canvas if it any coordinate less than 0`() {
        tupled(
            Canvas.create(Point(9, 9)),
            Line.create(Point.zero, Point(-1,0))
        ).flatMap {
            it.a.put(it.b)
        } shouldBeLeft ShapeCanNotBePlacedToCanvas
    }
}