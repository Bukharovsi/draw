package com.bukharov.drawing.drawing

sealed class Pixel {
    data class FiledPixel(val color: Char): Pixel()
    object Empty : Pixel()

    companion object {
        val X = FiledPixel('x');
    }
}