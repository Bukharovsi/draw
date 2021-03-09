package com.bukharov.drawing.app.command.application

import com.bukharov.drawing.app.command.Command
import com.bukharov.drawing.app.command.drawing.error.CanvasShouldBePresent
import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.drawing.pixel.asString
import com.bukharov.drawing.app.pixel.BorderDecoratorFactory
import java.io.PrintStream

class PrintCommand(
    private val borderFactory: BorderDecoratorFactory,
    private val printStream: PrintStream
) : Command {
    override fun execute(current: Canvas?): Canvas {
        if (null == current) throw CanvasShouldBePresent()
        printStream.println(
            borderFactory.create(current.rasterize())
                .asStrings()
                .asString()
        )
        return current
    }
}