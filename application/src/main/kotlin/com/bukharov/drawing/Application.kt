package com.bukharov.drawing

import com.bukharov.drawing.command.CommandFactory
import com.bukharov.drawing.drawing.Canvas
import java.util.*

fun main() {

    var workCanvas: Canvas? = null

    val reader = Scanner(System.`in`)
    while (true) {
        println("Please enter your command")
        val command = reader.nextLine()

        val foundCommand = CommandFactory.createCommand(command)

        if (null != foundCommand) {
            workCanvas = foundCommand.execute(workCanvas)
            println(workCanvas.print())
        } else {
            println("sorry command is not supported")
        }
    }
}
