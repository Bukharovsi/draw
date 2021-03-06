package com.bukharov.drawing.command

import com.bukharov.drawing.drawing.Canvas

interface Command {
    fun execute(current: Canvas?): Canvas

    interface Factory {
        fun tryToCreate(stringCommand: String): Command?
    }
}