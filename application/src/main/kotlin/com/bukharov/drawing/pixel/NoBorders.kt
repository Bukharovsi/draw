package com.bukharov.drawing.pixel

import com.bukharov.drawing.drawing.pixel.Printable

class NoBorders(
    private val wrapped: Printable
) : BorderDecorator {
    override val dimensions = wrapped.dimensions
    override fun asStrings() = wrapped.asStrings()

    object Factory : BorderDecoratorFactory {
        override fun create(printable: Printable): BorderDecorator =
            NoBorders(printable)
    }
}
