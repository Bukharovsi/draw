package com.bukharov.drawing.geometry

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class FieldTest {

    @Test
    fun `canvas with positive values width and height might be created`() {
        Field.create(
            rightUpperCorner = Point(6, 6)
        ) shouldBe Field(
            rightUpperCorner = Point(6, 6)
        )
    }

    @Test
    fun `two canvas with the same width and height are equal`() {
        Field(
            rightUpperCorner = Point(6, 6)
        ) shouldBe Field(
            rightUpperCorner = Point(6, 6)
        )
    }

    @Test
    fun `two canvas with the different width and height are NOT equal`() {
        Field(
            rightUpperCorner = Point(6, 6)
        ) shouldNotBe Field(
            rightUpperCorner = Point(6, 7)
        )
    }

    @Test
    fun `canvas with shape is not equal to empty one`() {
        Field(rightUpperCorner = Point(6, 6))
            .put(Line(Point.zero, Point(0, 1)))
            .shouldNotBe(Field(rightUpperCorner = Point(6, 6)))
    }

    @Test
    fun `if 2 canvas contain the same shapes - they are equal`() {
        Field(rightUpperCorner = Point(6, 6))
            .put(Line(Point.zero, Point(0, 1)))
            .shouldBe(
                Field(rightUpperCorner = Point(6, 6))
                    .put(Line(Point.zero, Point(0, 1)))
            )
    }

    @Test
    fun `canvas might not have width 0`() {
        shouldThrow<WrongCanvasSize> { Field.create(rightUpperCorner = Point(0, 6)) }
    }

    @Test
    fun `canvas might not have height 0`() {
        shouldThrow<WrongCanvasSize> { Field.create(rightUpperCorner = Point(6, 0)) }
    }

    @Test
    fun `a shape might be placed on canvas if it is within canvas`() {
        Field
            .create(Point(9, 9))
            .put(Line.create(Point.zero, Point(0, 3)))
            .should {
                it.shapes().size shouldBe 1
                it.shapes().contains(Line(Point.zero, Point(0, 3)))
            }
    }

    @Test
    fun `a shape might NOT be placed on canvas if it does not fit canvas`() {
        shouldThrow<ShapeCanNotBePlacedToCanvas> {
            Field
                .create(Point(9, 9))
                .put(Line.create(Point.zero, Point(0, 14)))
        }
    }

    @Test
    fun `a shape might not be placed on canvas if it any coordinate less than 0`() {
        shouldThrow<ShapeCanNotBePlacedToCanvas> {
            Field
                .create(Point(9, 9))
                .put(Line.create(Point.zero, Point(-1, 0)))
        }
    }
}