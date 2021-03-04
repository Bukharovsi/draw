package com.bukharov.drawing.command.application

import com.bukharov.drawing.drawing.DrawableCanvas
import kotlin.system.exitProcess

class QuitCommand : ApplicationCommand {

    override fun execute(current: DrawableCanvas?): DrawableCanvas {
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