package com.bukharov.drawing.command

import com.bukharov.drawing.drawing.DrawableCanvas

interface Command {
    fun execute(current: DrawableCanvas?): DrawableCanvas

    interface Factory {
        fun tryToCreate(stringCommand: String): Command?
    }
}