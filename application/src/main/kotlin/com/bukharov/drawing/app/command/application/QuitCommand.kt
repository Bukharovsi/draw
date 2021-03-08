package com.bukharov.drawing.app.command.application

import com.bukharov.drawing.drawing.Canvas
import kotlin.system.exitProcess

class QuitCommand : ApplicationCommand {

    override fun execute(current: Canvas?): Canvas {
        exitProcess(0)
    }

    class Factory : ApplicationCommand.Factory {

        override fun tryToCreate(stringCommand: String): ApplicationCommand? {
            return if (stringCommand.trim().toLowerCase() == "q") {
                QuitCommand()
            } else {
                null
            }
        }
    }
}