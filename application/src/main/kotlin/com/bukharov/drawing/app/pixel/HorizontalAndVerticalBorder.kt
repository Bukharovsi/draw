package com.bukharov.drawing.app.pixel

import com.bukharov.drawing.drawing.pixel.Printable

class HorizontalAndVerticalBorder(
    private val wrapped: Printable,
    private val verticalBorderDelimiter: Char = '|',
    private val horizontalBorderDelimiter: Char = '-'
) : BorderDecorator {
    override val dimensions = wrapped.dimensions

    override fun asStrings(): List<String> =
        mutableListOf<String>()
            .also { it.add(horizontalLine()) }
            .also { it.addAll(
                wrapped.asStrings().map {
                    "$verticalBorderDelimiter$it$verticalBorderDelimiter"
                }
            ) }
            .also { it.add(horizontalLine()) }

    private fun horizontalLine() = horizontalBorderDelimiter
        .toString()
        .repeat(dimensions.width + 2)

    object Factory : BorderDecoratorFactory {
        override fun create(printable: Printable) = HorizontalAndVerticalBorder(printable)
    }
}