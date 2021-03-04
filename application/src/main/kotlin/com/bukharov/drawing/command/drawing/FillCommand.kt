package com.bukharov.drawing.command.drawing

import com.bukharov.drawing.command.drawing.error.CanvasShouldBePresent
import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.geometry.Point

class FillCommand(
    private val target: Point,
    private val fillBy: Char
) : DrawingCommand {

    override fun execute(current: Canvas?): Canvas {
        if (current == null) throw CanvasShouldBePresent()

        return current
            .apply { fill(target) }
    }

    class Factory : DrawingCommand.Factory {
        val regex = Regex("^B (\\d+) (\\d+) (.)$", RegexOption.IGNORE_CASE)

        override fun tryToCreate(stringCommand: String): DrawingCommand? {
            val res = regex.find(stringCommand.trim()) ?: return null

            return FillCommand(
                target = Point(
                    x = res.groups[1]?.value?.toInt()
                        ?: throw NumberFormatException("int expected, but ${res.groups[1]?.value} given"),
                    y = res.groups[2]?.value?.toInt()
                        ?: throw NumberFormatException("int expected, but ${res.groups[1]?.value} given")
                ),
                fillBy = res.groups[2]?.value?.toCharArray()?.first()
                    ?: throw IllegalArgumentException("Illegal filling char given")
            )
        }
    }
}