package com.bukharov.drawing.command.drawing

import com.bukharov.drawing.command.drawing.error.CanvasShouldBePresent
import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.geometry.Line
import com.bukharov.drawing.geometry.Point

class DrawLineCommand(
    private val a: Point,
    private val b: Point
) : DrawingCommand {

    override fun execute(current: Canvas?): Canvas {
        if (null == current) throw CanvasShouldBePresent()
        current.put(Line.create(a, b))
        return current
    }

    class Factory : DrawingCommand.Factory {
        val regex = Regex("^L (\\d+) (\\d+) (\\d+) (\\d+)$", RegexOption.IGNORE_CASE)

        @Suppress("MagicNumber")
        override fun tryToCreate(stringCommand: String): DrawingCommand? {
            val cmd = regex.find(stringCommand.trim()) ?: return null

            return DrawLineCommand(
                a = Point(
                    x = cmd.groups[1]?.value?.toInt()
                        ?: throw NumberFormatException("int expected, but ${cmd.groups[1]?.value} given"),
                    y = cmd.groups[2]?.value?.toInt()
                        ?: throw NumberFormatException("int expected, but ${cmd.groups[1]?.value} given")
                ),
                b = Point(
                    x = cmd.groups[3]?.value?.toInt()
                        ?: throw NumberFormatException("int expected, but ${cmd.groups[1]?.value} given"),
                    y = cmd.groups[4]?.value?.toInt()
                        ?: throw NumberFormatException("int expected, but ${cmd.groups[1]?.value} given")
                ),
            )
        }
    }
}