package com.bukharov.drawing.geometry

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class RectangleTest {

    @Test
    fun `two rectangles are the same if their coordinates are the same`() {
        Rectangle(
            upperLeftCorner = Point(0, 3),
            lowerRightCorner = Point(3, 0)
        ) shouldBe Rectangle(
            upperLeftCorner = Point(0, 3),
            lowerRightCorner = Point(3, 0)
        )
    }

    @Test
    fun `two rectangles are different if lower right coordinates are different`() {
        Rectangle(
            upperLeftCorner = Point(0, 3),
            lowerRightCorner = Point(3, 0)
        ) shouldNotBe  Rectangle(
            upperLeftCorner = Point(0, 3),
            lowerRightCorner = Point(500, 0)
        )
    }

    @Test
    fun `two rectangles are different if upper left coordinates are different`() {
        Rectangle(
            upperLeftCorner = Point(0, 3),
            lowerRightCorner = Point(3, 0)
        ) shouldNotBe  Rectangle(
            upperLeftCorner = Point(2, 3),
            lowerRightCorner = Point(3, 0)
        )
    }

    @Test
    fun `if both coordinates are the same - can not create`() {
        val point = Point.zero
        shouldThrow<RectangleCoordinatesAreIncorrect> {
            Rectangle(
                upperLeftCorner = point,
                lowerRightCorner = point
            )
        }
    }

    @Test
    fun `if upper coordinate not more than lower - can not create`() {
        shouldThrow<RectangleCoordinatesAreIncorrect> {
            Rectangle(
                upperLeftCorner = Point(0, 2),
                lowerRightCorner = Point(3, 2)
            )
        }
    }

    @Test
    fun `if right coordinate less or equal to left - can not create`() {
        shouldThrow<RectangleCoordinatesAreIncorrect> {
            Rectangle(
                upperLeftCorner = Point(5, 20),
                lowerRightCorner = Point(3, 2)
            )
        }
    }

    @Test
    fun `if coordinates are correct - downLeftCorner and upperRightCorner are present`() {
        val rectangle = Rectangle(
            upperLeftCorner = Point(0, 3),
            lowerRightCorner = Point(3, 0)
        )

        rectangle.downLeftCorner() shouldBe Point(0, 0)
        rectangle.upperRightCorner() shouldBe Point(3, 3)
    }
}