package com.bukharov.drawing.command

import com.bukharov.drawing.command.application.QuitCommand
import com.bukharov.drawing.command.drawing.CreateCanvasCommand
import com.bukharov.drawing.command.drawing.DrawLineCommand
import com.bukharov.drawing.command.drawing.FillCommand

object CommandFactory {

    private val commandFactories: List<Command.Factory> = listOf(
        CreateCanvasCommand.Factory(),
        DrawLineCommand.Factory(),
        FillCommand.Factory(),
        QuitCommand.Factory()
    )

    fun createCommand(command: String) = commandFactories
            .map { factory -> factory.tryToCreate(command) }
            .firstOrNull { cmd -> cmd != null }
}