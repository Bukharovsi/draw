package com.bukharov.drawing.geometry

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class FieldTest {

    @Test
    fun `canvas with positive values width and height might be created`() {
        Field(
            upperRightCorner = Point(6, 6)
        ) shouldBe Field(
            upperRightCorner = Point(6, 6)
        )
    }

    @Test
    fun `two canvas with the same width and height are equal`() {
        Field(
            upperRightCorner = Point(6, 6)
        ) shouldBe Field(
            upperRightCorner = Point(6, 6)
        )
    }

    @Test
    fun `two canvas with the same width and height but different lower left corner are NOT equal`() {
        Field(
            upperRightCorner = Point(6, 6),
            lowerLeftCorner = Point.zero
        ) shouldNotBe Field(
            upperRightCorner = Point(6, 6),
            lowerLeftCorner = Point(1, 1)
        )
    }

    @Test
    fun `two canvas with the different width and height are NOT equal`() {
        Field(
            upperRightCorner = Point(6, 6)
        ) shouldNotBe Field(
            upperRightCorner = Point(6, 7)
        )
    }

    @Test
    fun `canvas with shape is not equal to empty one`() {
        Field(upperRightCorner = Point(6, 6))
            .put(Line(Point.zero, Point(0, 1)))
            .shouldNotBe(Field(upperRightCorner = Point(6, 6)))
    }

    @Test
    fun `if 2 canvas contain the same shapes - they are equal`() {
        Field(upperRightCorner = Point(6, 6))
            .put(Line(Point.zero, Point(0, 1)))
            .shouldBe(
                Field(upperRightCorner = Point(6, 6))
                    .put(Line(Point.zero, Point(0, 1)))
            )
    }

    @Test
    fun `canvas might not have width 0`() {
        shouldThrow<WrongCanvasSize> { Field(upperRightCorner = Point(0, 6)) }
    }

    @Test
    fun `canvas might not have height 0`() {
        shouldThrow<WrongCanvasSize> { Field(upperRightCorner = Point(6, 0)) }
    }

    @Test
    fun `a shape might be placed on canvas if it is within canvas`() {
        Field(Point(9, 9))
            .put(Line.create(Point.zero, Point(0, 3)))
            .should {
                it.shapes().size shouldBe 1
                it.shapes().contains(Line(Point.zero, Point(0, 3)))
            }
    }

    @ParameterizedTest
    @CsvSource(
        "2, 14, 2, 4",
        "0, 4, 0, 2"
    )
    fun `a shape might NOT be placed on canvas if it does not fit canvas`(
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int
    ) {
        shouldThrow<ShapeCanNotBePlacedToCanvas> {
            Field(upperRightCorner = Point(9, 9), lowerLeftCorner = Point(1, 1))
                .put(Line.create(Point(x1, y1), Point(x2, y2)))
        }
    }

    @Test
    fun `a shape might not be placed on canvas if it any coordinate less than 0`() {
        shouldThrow<ShapeCanNotBePlacedToCanvas> {
            Field(Point(9, 9))
                .put(Line.create(Point.zero, Point(-1, 0)))
        }
    }
}