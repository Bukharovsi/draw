package com.bukharov.drawing.command.drawing

import com.bukharov.drawing.command.Command

interface DrawingCommand : Command {

    interface Factory : Command.Factory {
        override fun tryToCreate(stringCommand: String): DrawingCommand?
    }
}