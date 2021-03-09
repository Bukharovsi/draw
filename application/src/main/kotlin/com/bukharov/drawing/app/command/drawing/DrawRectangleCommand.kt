package com.bukharov.drawing.app.command.drawing

import com.bukharov.drawing.app.command.drawing.error.CanvasShouldBePresent
import com.bukharov.drawing.app.command.util.groupAsIntOrThrow
import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.geometry.Line
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.Rectangle

class DrawRectangleCommand(
    private val upperLeftCorner: Point,
    private val lowerRightCorner: Point
) : DrawingCommand {

    override fun execute(current: Canvas?): Canvas {
        if (null == current) throw CanvasShouldBePresent()
        current.put(
            Rectangle(
                upperLeftCorner = upperLeftCorner,
                lowerRightCorner = lowerRightCorner
            )
        )
        return current
    }

    class Factory : DrawingCommand.Factory {
        val regex = Regex("^R (\\d+) (\\d+) (\\d+) (\\d+)$", RegexOption.IGNORE_CASE)

        @Suppress("MagicNumber")
        override fun tryToCreate(stringCommand: String): DrawingCommand? {
            val cmd = regex.find(stringCommand.trim()) ?: return null

            return DrawRectangleCommand(
                upperLeftCorner = Point(
                    x = cmd.groupAsIntOrThrow(1),
                    y = cmd.groupAsIntOrThrow(2)
                ),
                lowerRightCorner = Point(
                    x = cmd.groupAsIntOrThrow(3),
                    y = cmd.groupAsIntOrThrow(4)
                ),
            )
        }
    }
}