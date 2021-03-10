package com.bukharov.drawing.geometry

class Rectangle(
    val upperLeftCorner: Point,
    val lowerRightCorner: Point
) : Shape {

    override val lowerLeftCorner = Point(x = upperLeftCorner.x, y = lowerRightCorner.y)
    override val upperRightCorner = Point(x = lowerRightCorner.x, y = upperLeftCorner.y)

    init {
        if (upperLeftCorner.y <= lowerRightCorner.y) {
            throw RectangleCoordinatesAreIncorrect(
                upperLeftCorner,
                lowerRightCorner,
                "upper corner is lower or equal to lower corner"
            )
        }

        if (upperLeftCorner.x >= lowerRightCorner.x) {
            throw RectangleCoordinatesAreIncorrect(
                upperLeftCorner,
                lowerRightCorner,
                "left corner is roghter or equal to right corner"
            )
        }
    }

    fun edges() = setOf(
        Line(upperLeftCorner, upperRightCorner),
        Line(upperRightCorner, lowerRightCorner),
        Line(lowerRightCorner, lowerLeftCorner),
        Line(lowerLeftCorner, upperLeftCorner)
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

    companion object {
        fun createUsing2DiagonalCoordinates(a: Point, b: Point): Rectangle {
            val left = minXof(a, b)
            val right = maxXof(a, b)
            val lower = minYof(a, b)
            val upper = maxYof(a, b)
            return Rectangle(
                upperLeftCorner = Point(x = left, y = upper),
                lowerRightCorner = Point(x = right, y = lower)
            )
        }
    }
}

class RectangleCoordinatesAreIncorrect(
    val upperLeftCorner: Point,
    val lowerRightCorner: Point,
    val reason: String
) : UserReadableError, IllegalArgumentException() {
    override fun message() =
        "Rectangle with coordinates " +
            "upper left corner $upperLeftCorner, " +
            "lower right corner $lowerRightCorner" +
            " can not be created because $reason"
}