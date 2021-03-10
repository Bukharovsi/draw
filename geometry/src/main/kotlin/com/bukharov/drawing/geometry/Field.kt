package com.bukharov.drawing.geometry

class Field(
    val upperRightCorner: Point,
    private val lowerLeftCorner: Point = Point.zero
) {

    private val shapes: MutableSet<Shape> = mutableSetOf()

    init {
        if (upperRightCorner.x <= lowerLeftCorner.x || upperRightCorner.y <= lowerLeftCorner.y) {
            throw WrongCanvasSize(upperRightCorner)
        }
    }

    fun put(shape: Shape): Field {
        if (shape.upperRightCorner.moreByAnyDirectionThan(upperRightCorner)) {
            throw ShapeCanNotBePlacedToCanvas(shape, this)
        }

        if (shape.lowerLeftCorner.lessByAnyDirectionThan(lowerLeftCorner)) {
            throw ShapeCanNotBePlacedToCanvas(shape, this)
        }

        shapes.add(shape)
        return this
    }

    fun shapes(): Set<Shape> = shapes

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Field) return false

        if (upperRightCorner != other.upperRightCorner) return false
        if (lowerLeftCorner != other.lowerLeftCorner) return false
        if (shapes != other.shapes()) return false
        return true
    }

    override fun toString(): String {
        return "Canvas([$upperRightCorner, $lowerLeftCorner] shapes=$shapes)"
    }

    override fun hashCode(): Int {
        var result = upperRightCorner.hashCode()
        result = 31 * result + lowerLeftCorner.hashCode()
        result = 31 * result + shapes.hashCode()
        return result
    }
}

class ShapeCanNotBePlacedToCanvas(
    val shape: Shape,
    val field: Field
) : UserReadableError, IllegalArgumentException() {

    override fun message() =
        "Shape with dimensions from ${shape.lowerLeftCorner}  ${shape.upperRightCorner} " +
            "can not be placed to field with size ${field.upperRightCorner}"
}

class WrongCanvasSize(val wrongSize: Point) : UserReadableError, IllegalArgumentException() {
    override fun message() = "Incorrect size $wrongSize given"
}