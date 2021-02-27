package com.bukharov.drawing.geometry

data class Dimensions internal constructor(
    val width: Int,
    val height: Int
) {

    fun toUpperRightCoordinate(): Point =
        Point(x = width - 1, y = height - 1)

    companion object {
        fun create(width: Int, height: Int) =
            if (width <= 0 || height <= 0) throw DimensionMustBePositive(width, height)
            else Dimensions(width = width, height = height)
    }
}

fun Point.toDimension() = Dimensions.create(width = this.x + 1, height = this.y + 1)

data class DimensionMustBePositive(val width: Int, val height: Int) : IllegalArgumentException()