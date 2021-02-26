package com.bukharov.drawing.drawing

sealed class Pixel {

    abstract fun print(): Char

    data class FiledPixel(private val color: Char) : Pixel() {
        override fun print() = color
    }

    object Empty : Pixel() {
        override fun print() = ' '
    }

    companion object {
        val X = FiledPixel('x')
    }
}