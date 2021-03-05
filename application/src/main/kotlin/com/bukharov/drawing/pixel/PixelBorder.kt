package com.bukharov.drawing.pixel

import com.bukharov.drawing.drawing.pixel.Printable

class PixelBorder(
    private val wrapped: Printable,
    private val verticalBorderDelimiter: Char = '|',
    private val horizontalBorderDelimiter: Char = '-'
) : Printable {
    override val dimensions = wrapped.dimensions

    override fun print(): List<String> =
        mutableListOf<String>()
            .also { it.add(horizontalLine()) }
            .also { it.addAll(
                wrapped.print().map {
                    "$verticalBorderDelimiter$it$verticalBorderDelimiter"
                }
            ) }
            .also { it.add(horizontalLine()) }

    private fun horizontalLine() = horizontalBorderDelimiter
        .toString()
        .repeat(dimensions.width + 2)
}