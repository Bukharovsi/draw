package com.bukharov.drawing.app.command.drawing

import com.bukharov.drawing.app.command.util.groupAsIntOrThrow
import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.drawing.DrawableCanvas
import com.bukharov.drawing.geometry.Dimensions

class CreateCanvasCommand(
    private val size: Dimensions
) : DrawingCommand {

    override fun execute(current: Canvas?): Canvas {
        return DrawableCanvas(size)
    }

    class Factory : DrawingCommand.Factory {
        val regex = Regex("^C (\\d+) (\\d+)$", RegexOption.IGNORE_CASE)

        override fun tryToCreate(stringCommand: String): DrawingCommand? {
            val cmd = regex.find(stringCommand.trim()) ?: return null

            return CreateCanvasCommand(Dimensions(
                width = cmd.groupAsIntOrThrow(1),
                height = cmd.groupAsIntOrThrow(2)
            ))
        }
    }
}