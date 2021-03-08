package com.bukharov.drawing.app.command.drawing

import com.bukharov.drawing.app.command.Command

interface DrawingCommand : Command {

    interface Factory : Command.Factory {
        override fun tryToCreate(stringCommand: String): DrawingCommand?
    }
}