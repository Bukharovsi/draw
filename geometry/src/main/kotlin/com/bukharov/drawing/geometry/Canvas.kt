package com.bukharov.drawing.geometry

import arrow.core.Either
import arrow.core.left
import arrow.core.right

class Canvas(
    val rightUpperCorner: Point
) {

    private val leftDownCorner = Point.zero

    private val shapes: MutableSet<Line> = mutableSetOf()

    @Suppress("ReturnCount")
    fun put(shape: Line): Either<DrawingError, Canvas> {
        if (shape.upperRightCorner().moreByAnyDirectionThan(rightUpperCorner)) {
            return ShapeCanNotBePlacedToCanvas.left()
        }

        if (shape.downLeftCorner().lessByAnyDirectionThan(leftDownCorner)) {
            return ShapeCanNotBePlacedToCanvas.left()
        }

        shapes.add(shape)
        return this.right()
    }

    fun shapes(): Set<Line> = shapes

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Canvas) return false

        if (rightUpperCorner != other.rightUpperCorner) return false

        return true
    }

    override fun toString(): String {
        return "Canvas(rightUpperCorner=$rightUpperCorner, shapes=$shapes)"
    }

    override fun hashCode(): Int {
        var result = rightUpperCorner.hashCode()
        result = 31 * result + leftDownCorner.hashCode()
        result = 31 * result + shapes.hashCode()
        return result
    }

    companion object {
        fun create(rightUpperCorner: Point): Either<DrawingError, Canvas> {
            if (rightUpperCorner.x < 1 || rightUpperCorner.y < 1) {
                return WrongCanvasSize.left()
            }

            return Canvas(rightUpperCorner).right()
        }
    }
}

object ShapeCanNotBePlacedToCanvas : DrawingError
object WrongCanvasSize : DrawingError