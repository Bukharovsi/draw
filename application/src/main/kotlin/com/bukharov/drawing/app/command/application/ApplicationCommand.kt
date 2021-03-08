package com.bukharov.drawing.app.command.application

import com.bukharov.drawing.app.command.Command

/**
 * Application command does not Canvas, and actually, application commands and drawing commands
 * should not have the same interface, but to simplify solution they are using the same interface.
 * Otherwise it requires some command executor that provides unified interface for the application.
 */
interface ApplicationCommand : Command {

    interface Factory : Command.Factory {
        override fun tryToCreate(stringCommand: String): ApplicationCommand?
    }
}