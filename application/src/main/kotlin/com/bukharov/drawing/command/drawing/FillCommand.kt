package com.bukharov.drawing.command.drawing

import com.bukharov.drawing.command.LayoutMustBeProvided
import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.geometry.Point

class FillCommand(
    private val target: Point
) : DrawingCommand {

    override fun execute(current: Canvas?): Canvas {
        if (current == null) throw LayoutMustBeProvided()

        return current
            .apply { fill(target) }
    }
}