package com.bukharov.drawing.geometry

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right

data class Line internal constructor(
    val a: Point,
    val b: Point
) : Shape {

    private fun isHorizontal(): Boolean =
        a.y == b.y

    private fun isVertical(): Boolean =
        a.x == b.x

    override fun downLeftCorner(): Point = lowestLeftOfPoints(a, b)
    override fun upperRightCorner(): Point = upperRightOfPoints(a, b)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Line) return false

        if (a == other.a && b == other.b) return true
        if (a == other.b && b == other.a) return true

        return true
    }

    override fun hashCode(): Int =
        31 * (b.hashCode() + a.hashCode())

    companion object {
        fun create(from: Point, to: Point): Either<DrawingError, Line> {
            val line = if (from == to) LineShouldNotBePoint.left()
            else Line(from, to).right()

            return line.flatMap {
                return if (!(it.isHorizontal() || it.isVertical())) {
                    LineShouldBeVerticalOrHorizontal.left()
                } else {
                    it.right()
                }
            }
        }
    }
}

object LineShouldNotBePoint : DrawingError
object LineShouldBeVerticalOrHorizontal : DrawingError