package com.bukharov.drawing.geometry

class Canvas(
    val rightUpperCorner: Point
) {

    private val leftDownCorner = Point.zero

    private val shapes: MutableSet<Line> = mutableSetOf()

    fun put(shape: Line): Canvas {
        if (shape.upperRightCorner().moreByAnyDirectionThan(rightUpperCorner)) {
            throw ShapeCanNotBePlacedToCanvas(shape, this)
        }

        if (shape.downLeftCorner().lessByAnyDirectionThan(leftDownCorner)) {
            throw ShapeCanNotBePlacedToCanvas(shape, this)
        }

        shapes.add(shape)
        return this
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
        fun create(rightUpperCorner: Point): Canvas {
            if (rightUpperCorner.x < 1 || rightUpperCorner.y < 1) {
                throw WrongCanvasSize(rightUpperCorner)
            }

            return Canvas(rightUpperCorner)
        }
    }
}

class ShapeCanNotBePlacedToCanvas(val shape: Shape, val canvas: Canvas) : IllegalArgumentException()
class WrongCanvasSize(val wrongSize: Point) : IllegalArgumentException()