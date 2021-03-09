package com.bukharov.drawing.geometry

data class Dimensions constructor(
    val width: Int,
    val height: Int
) {

    init {
        if (width <= 0 || height <= 0) throw DimensionMustBePositive(width, height)
    }

    fun toUpperRightCoordinate(): Point =
        Point(x = width - 1, y = height - 1)

    fun moreByAnyDirectionThan(other: Dimensions) =
        this.toUpperRightCoordinate()
            .moreByAnyDirectionThan(other.toUpperRightCoordinate())

    fun lessByAnyDirectionThan(other: Dimensions) =
        this.toUpperRightCoordinate()
            .lessByAnyDirectionThan(other.toUpperRightCoordinate())
}

fun Point.toDimension() = Dimensions(width = this.x + 1, height = this.y + 1)

data class DimensionMustBePositive(val width: Int, val height: Int) : UserReadableError, IllegalArgumentException() {
    override fun message() = "Dimensions must be positive, $width and $height given"
}