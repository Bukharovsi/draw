package com.bukharov.drawing.app.command.application

import com.bukharov.drawing.app.command.Command
import com.bukharov.drawing.app.command.drawing.error.CanvasShouldBePresent
import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.drawing.pixel.print
import com.bukharov.drawing.app.pixel.BorderDecoratorFactory

class PrintCommand(
    private val borderFactory: BorderDecoratorFactory
) : Command {
    override fun execute(current: Canvas?): Canvas {
        if (null == current) throw CanvasShouldBePresent()
        println(
            borderFactory.create(current.rasterize())
                .asStrings()
                .print()
        )
        return current
    }
}