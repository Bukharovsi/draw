package com.bukharov.drawing.geometry

class Field(
    val rightUpperCorner: Point
) {

    private val leftDownCorner = Point.zero

    private val shapes: MutableSet<Shape> = mutableSetOf()

    init {
        if (rightUpperCorner.x < 1 || rightUpperCorner.y < 1) {
            throw WrongCanvasSize(rightUpperCorner)
        }
    }

    fun put(shape: Shape): Field {
        if (shape.upperRightCorner().moreByAnyDirectionThan(rightUpperCorner)) {
            throw ShapeCanNotBePlacedToCanvas(shape, this)
        }

        if (shape.lowerLeftCorner().lessByAnyDirectionThan(leftDownCorner)) {
            throw ShapeCanNotBePlacedToCanvas(shape, this)
        }

        shapes.add(shape)
        return this
    }

    fun shapes(): Set<Shape> = shapes

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Field) return false

        if (rightUpperCorner != other.rightUpperCorner) return false
        if (shapes != other.shapes()) return false
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
}

class ShapeCanNotBePlacedToCanvas(
    val shape: Shape,
    val field: Field
) : UserReadableError, IllegalArgumentException() {

    override fun message() =
        "Shape with dimensions from ${shape.lowerLeftCorner()}  ${shape.upperRightCorner()} " +
            "can not be placed to field with size ${field.rightUpperCorner}"
}

class WrongCanvasSize(val wrongSize: Point) : UserReadableError, IllegalArgumentException() {
    override fun message() = "Incorrect size $wrongSize given"
}