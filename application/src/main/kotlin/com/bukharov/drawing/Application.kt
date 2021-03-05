package com.bukharov.drawing

import com.bukharov.drawing.command.CommandFactory
import com.bukharov.drawing.drawing.DrawableCanvas
import com.bukharov.drawing.drawing.pixel.print
import com.bukharov.drawing.pixel.PixelBorder
import java.util.Scanner

fun main() {

    var workCanvas: DrawableCanvas? = null

    val reader = Scanner(System.`in`)
    while (true) {
        println("Please enter your command")
        val command = reader.nextLine()

        val foundCommand = CommandFactory.createCommand(command)

        if (null != foundCommand) {
            workCanvas = foundCommand.execute(workCanvas)
            println(PixelBorder(workCanvas.rasterize()).print().print())
        } else {
            println("sorry command is not supported")
        }
    }
}
