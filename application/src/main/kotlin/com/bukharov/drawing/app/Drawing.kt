package com.bukharov.drawing.app

import com.bukharov.drawing.app.command.CommandFactory
import com.bukharov.drawing.app.command.application.PrintCommand
import com.bukharov.drawing.drawing.Canvas
import com.bukharov.drawing.app.pixel.BorderDecoratorFactory
import com.bukharov.drawing.app.pixel.HorizontalAndVerticalBorder
import com.bukharov.drawing.geometry.UserReadableError
import java.io.InputStream
import java.io.PrintStream
import java.util.Scanner

@Suppress("TooGenericExceptionCaught", "NestedBlockDepth")
class Drawing(
    private val inStream: InputStream,
    private val outStream: PrintStream
) {

    private val borderFactory: BorderDecoratorFactory = HorizontalAndVerticalBorder.Factory
    private val printCommand = PrintCommand(borderFactory, outStream)
    private val reader = Scanner(inStream)

    fun run() {
        var workCanvas: Canvas? = null
        while (outStream.println("Please enter your command") == Unit && reader.hasNextLine()) {
            val command = reader.nextLine()
            val foundCommand = CommandFactory.createCommand(command)
            if (null != foundCommand) {
                try {
                    workCanvas = foundCommand.execute(workCanvas)
                    printCommand.execute(workCanvas)
                } catch (e: Throwable) {
                    if (e is UserReadableError) {
                        outStream.println(e.message())
                    } else {
                        // todo add error handler
                        outStream.println("Unhandled exception: " + e::class.toString() + " " + e.message)
                    }
                }
            } else {
                outStream.println("Sorry, command is not supported")
            }
        }
    }
}
