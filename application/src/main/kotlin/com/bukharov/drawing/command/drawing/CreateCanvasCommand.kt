package com.bukharov.drawing.command.drawing

import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.geometry.Dimensions

class CreateCanvasCommand(
    private val size: Dimensions
) : DrawingCommand {

    override fun execute(current: Canvas?): Canvas {
        TODO("Not yet implemented")
    }
}