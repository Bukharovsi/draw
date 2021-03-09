package com.bukharov.drawing.geometry

interface Shape {
    fun lowerLeftCorner(): Point
    fun upperRightCorner(): Point
}