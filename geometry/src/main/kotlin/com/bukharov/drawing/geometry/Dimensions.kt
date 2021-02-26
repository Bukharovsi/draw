package com.bukharov.drawing.geometry

data class Dimensions(
    val width: Int,
    val height: Int
) {

    fun toUpperRightCoordinate(): Point =
        Point(x = width - 1, y = height - 1)
}

fun Point.toDimension() = Dimensions(width = this.x + 1, height = this.y + 1)