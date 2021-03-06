package com.bukharov.drawing.command.application

import com.bukharov.drawing.command.Command
import com.bukharov.drawing.command.drawing.error.CanvasShouldBePresent
import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.drawing.pixel.print
import com.bukharov.drawing.pixel.BorderDecoratorFactory

class PrintCommand(
    val borderFactory: BorderDecoratorFactory
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