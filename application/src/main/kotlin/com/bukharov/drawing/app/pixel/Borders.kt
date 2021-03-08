package com.bukharov.drawing.app.pixel

import com.bukharov.drawing.drawing.pixel.Printable

interface BorderDecorator : Printable
interface BorderDecoratorFactory {
    fun create(printable: Printable): BorderDecorator
}