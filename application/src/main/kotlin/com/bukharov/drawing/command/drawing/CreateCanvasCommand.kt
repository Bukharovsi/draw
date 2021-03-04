package com.bukharov.drawing.command.drawing

import com.bukharov.drawing.drawing.DrawableCanvas
import com.bukharov.drawing.geometry.Dimensions

class CreateCanvasCommand(
    private val size: Dimensions
) : DrawingCommand {

    override fun execute(current: DrawableCanvas?): DrawableCanvas {
        return DrawableCanvas(size)
    }

    class Factory : DrawingCommand.Factory {
        val regex = Regex("^C (\\d+) (\\d+)$", RegexOption.IGNORE_CASE)

        override fun tryToCreate(stringCommand: String): DrawingCommand? {
            val res = regex.find(stringCommand.trim()) ?: return null

            return CreateCanvasCommand(Dimensions(
                width = res.groups[1]?.value?.toInt()
                    ?: throw NumberFormatException("int expected, but ${res.groups[1]?.value} given"),
                height = res.groups[2]?.value?.toInt()
                    ?: throw NumberFormatException("int expected, but ${res.groups[1]?.value} given"))
            )
        }
    }
}