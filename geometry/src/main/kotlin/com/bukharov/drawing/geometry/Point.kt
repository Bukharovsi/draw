package com.bukharov.drawing.geometry

data class Point(
    val x: Int,
    val y: Int
) {
    fun moreByAnyDirectionThan(other: Point) =
        x > other.x || y > other.y

    fun lessByAnyDirectionThan(other: Point) =
        x < other.x || y < other.y

    companion object {
        val zero = Point(0, 0)
    }
}

fun upperRightOfPoints(vararg points: Point) =
    Point(
        x = maxXof(*points),
        y = maxYof(*points)
    )

fun lowestLeftOfPoints(vararg points: Point): Point =
    Point(
        x = minXof(*points),
        y = minYof(*points)
    )

fun maxXof(vararg points: Point): Int = points.maxOf { it.x }
fun maxYof(vararg points: Point): Int = points.maxOf { it.y }
fun minXof(vararg points: Point): Int = points.minOf { it.x }
fun minYof(vararg points: Point): Int = points.minOf { it.y }

fun Point.incY() = Point(this.x, this.y + 1)
fun Point.decY() = Point(this.x, this.y - 1)
fun Point.incX() = Point(this.x + 1, this.y)
fun Point.decX() = Point(this.x - 1, this.y)