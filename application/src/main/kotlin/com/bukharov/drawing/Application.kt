package com.bukharov.drawing

import com.bukharov.drawing.command.CommandFactory
import com.bukharov.drawing.command.application.PrintCommand
import com.bukharov.drawing.drawing.DrawableCanvas
import com.bukharov.drawing.pixel.BorderDecoratorFactory
import com.bukharov.drawing.pixel.HorizontalAndVerticalBorder
import java.util.Scanner

fun main() {
    val borderFactory: BorderDecoratorFactory = HorizontalAndVerticalBorder.Factory
    val printCommand = PrintCommand(borderFactory)
    val reader = Scanner(System.`in`)

    var workCanvas: DrawableCanvas? = null
    while (true) {
        println("Please enter your command")
        val command = reader.nextLine()
        val foundCommand = CommandFactory.createCommand(command)
        if (null != foundCommand) {
            workCanvas = foundCommand.execute(workCanvas)
            printCommand.execute(workCanvas)
        } else {
            println("Sorry, command is not supported")
        }
    }
}
