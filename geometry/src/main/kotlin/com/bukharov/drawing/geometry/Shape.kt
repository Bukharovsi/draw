package com.bukharov.drawing.geometry

interface Shape {
    fun downLeftCorner(): Point
    fun upperRightCorner(): Point
}