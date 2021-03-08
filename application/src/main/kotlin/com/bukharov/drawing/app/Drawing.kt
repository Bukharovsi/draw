package com.bukharov.drawing.app

import com.bukharov.drawing.app.command.CommandFactory
import com.bukharov.drawing.app.command.application.PrintCommand
import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.app.pixel.BorderDecoratorFactory
import com.bukharov.drawing.app.pixel.HorizontalAndVerticalBorder
import java.util.Scanner

@Suppress("TooGenericExceptionCaught")
class Drawing {

    val borderFactory: BorderDecoratorFactory = HorizontalAndVerticalBorder.Factory
    val printCommand = PrintCommand(borderFactory)
    val reader = Scanner(System.`in`)

    fun run() {
        var workCanvas: Canvas? = null
        while (true) {
            println("Please enter your command")
            val command = reader.nextLine()
            val foundCommand = CommandFactory.createCommand(command)
            if (null != foundCommand) {
                try {
                    workCanvas = foundCommand.execute(workCanvas)
                    printCommand.execute(workCanvas)
                } catch (e: Throwable) {
                    // todo add error handler
                    println(e.message)
                }
            } else {
                println("Sorry, command is not supported")
            }
        }
    }
}
