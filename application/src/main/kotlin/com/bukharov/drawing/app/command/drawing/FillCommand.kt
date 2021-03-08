package com.bukharov.drawing.app.command.drawing

import com.bukharov.drawing.app.command.drawing.error.CanvasShouldBePresent
import com.bukharov.drawing.app.command.util.groupAsCharOrThrow
import com.bukharov.drawing.app.command.util.groupAsIntOrThrow
import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.geometry.Point

class FillCommand(
    private val target: Point,
    private val fillBy: Char
) : DrawingCommand {

    override fun execute(current: Canvas?): Canvas {
        if (current == null) throw CanvasShouldBePresent()

        return current
            .apply { fill(target, Pixel.FiledPixel(fillBy)) }
    }

    class Factory : DrawingCommand.Factory {
        val regex = Regex("^B (\\d+) (\\d+) (.)$", RegexOption.IGNORE_CASE)

        @Suppress("MagicNumber")
        override fun tryToCreate(stringCommand: String): DrawingCommand? {
            val cmd = regex.find(stringCommand.trim()) ?: return null

            return FillCommand(
                target = Point(
                    x = cmd.groupAsIntOrThrow(1),
                    y = cmd.groupAsIntOrThrow(2)
                ),
                fillBy = cmd.groupAsCharOrThrow(3)
            )
        }
    }
}