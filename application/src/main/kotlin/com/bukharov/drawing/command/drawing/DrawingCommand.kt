package com.bukharov.drawing.command.drawing

import com.bukharov.drawing.drawing.Canvas

interface DrawingCommand {
    fun execute(current: Canvas?): Canvas
}