package com.bukharov.drawing.command.drawing

import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.geometry.Point

class DrawLineCommand(
    private val a: Point,
    private val b: Point
) : DrawingCommand {

    override fun execute(current: Canvas?): Canvas {
        TODO()
    }
}