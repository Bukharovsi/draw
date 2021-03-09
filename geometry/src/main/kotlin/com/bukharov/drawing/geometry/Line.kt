package com.bukharov.drawing.geometry

data class Line internal constructor(
    val a: Point,
    val b: Point
) : Shape {

    fun isHorizontal(): Boolean =
        a.y == b.y

    fun isVertical(): Boolean =
        a.x == b.x

    override fun downLeftCorner(): Point = lowestLeftOfPoints(a, b)
    override fun upperRightCorner(): Point = upperRightOfPoints(a, b)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Line) return false

        if (a == other.a && b == other.b) return true
        if (a == other.b && b == other.a) return true

        return false
    }

    override fun hashCode(): Int =
        31 * (b.hashCode() + a.hashCode())

    companion object {
        fun create(from: Point, to: Point): Line {
            val line = if (from == to) throw LineShouldNotBePoint(from) else Line(from, to)

            if (!(line.isHorizontal() || line.isVertical())) {
                throw LineShouldBeVerticalOrHorizontal(line)
            }
            return line
        }
    }
}

class LineShouldNotBePoint(val coordinate: Point) : UserReadableError, IllegalArgumentException() {
    override fun message() = "line should have length > 1. Coordinates $coordinate is incorrect"
}

class LineShouldBeVerticalOrHorizontal(val line: Line) : UserReadableError, IllegalArgumentException() {
    override fun message() = "Only horizontal and vertical lines are supported yet"
}