package com.bukharov.drawing.drawing.pixel

sealed class Pixel {

    abstract fun print(): Char
    abstract fun mergeAtop(atop: Pixel): Pixel

    data class FiledPixel(private val color: Char) : Pixel() {
        override fun print() = color
        override fun mergeAtop(atop: Pixel): Pixel =
            if (atop != Empty) atop
            else this
    }

    object Empty : Pixel() {
        override fun print() = ' '
        override fun mergeAtop(atop: Pixel) = atop
    }

    companion object {
        val X = FiledPixel('x')
        val O = FiledPixel('o')
    }
}