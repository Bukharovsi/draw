package com.bukharov.drawing.geometry

class Rectangle(
    val upperLeftCorner: Point,
    val lowerRightCorner: Point
) : Shape {

    init {
        if (upperLeftCorner.y <= lowerRightCorner.y) {
            throw RectangleCoordinatesAreIncorrect(upperLeftCorner, lowerRightCorner)
        }

        if (upperLeftCorner.x >= lowerRightCorner.x) {
            throw RectangleCoordinatesAreIncorrect(upperLeftCorner, lowerRightCorner)
        }
    }

    fun edges() = setOf(
        Line(upperLeftCorner, upperRightCorner()),
        Line(upperRightCorner(), lowerRightCorner),
        Line(lowerRightCorner, downLeftCorner()),
        Line(downLeftCorner(), upperLeftCorner)
    )

    override fun downLeftCorner() =
        Point(
            x = upperLeftCorner.x,
            y = lowerRightCorner.y
        )

    override fun upperRightCorner() =
        Point(
            x = lowerRightCorner.x,
            y = upperLeftCorner.y
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rectangle) return false

        if (upperLeftCorner != other.upperLeftCorner) return false
        if (lowerRightCorner != other.lowerRightCorner) return false

        return true
    }

    override fun hashCode(): Int {
        var result = upperLeftCorner.hashCode()
        result = 31 * result + lowerRightCorner.hashCode()
        return result
    }

    override fun toString(): String {
        return "Rectangle(upperLeftCorner=$upperLeftCorner, lowerRightCorner=$lowerRightCorner)"
    }
}

class RectangleCoordinatesAreIncorrect(
    val upperLeftCorner: Point,
    val lowerRightCorner: Point
) : IllegalArgumentException()