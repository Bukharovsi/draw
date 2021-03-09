package com.bukharov.drawing.app

import io.kotest.matchers.shouldBe
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.junit.jupiter.api.Test

/**
 * Some kind of smock tests
 *
 * Does not intend to test actual functionality or edge cases,
 * but it should be used for testing overall component integration
 */
@Suppress("LongMethod")
internal class DrawingTest {

    @Test
    fun `run application and draw canvas`() {
        val expectedOutput = "Please enter your command\n" +
            "----------------------\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n" +
            "\n" +
            "Please enter your command" +
            "\n"

        val input = ByteArrayInputStream("C 20 10".toByteArray())
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val app = Drawing(input, printStream)
        app.run()

        val actualOutput = String(outputStream.toByteArray())

        actualOutput shouldBe expectedOutput
    }

    @Test
    fun `run application and draw shapes`() {
        val expectedOutput = "Please enter your command\n" +
            "----------------------\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n" +
            "\n" +
            "Please enter your command\n" +
            "----------------------\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "----------------------\n" +
            "\n" +
            "Please enter your command\n" +
            "----------------------\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "|xxxxxxxxx           |\n" +
            "|     x              |\n" +
            "----------------------\n" +
            "\n" +
            "Please enter your command\n" +
            "----------------------\n" +
            "|cccccccccccccccccccc|\n" +
            "|cccccccccccccccccccc|\n" +
            "|cccccccccccccccccccc|\n" +
            "|cccccccccccccccccccc|\n" +
            "|cccccxcccccccccccccc|\n" +
            "|cccccxcccccccccccccc|\n" +
            "|cccccxcccccccccccccc|\n" +
            "|cccccxcccccccccccccc|\n" +
            "|xxxxxxxxxccccccccccc|\n" +
            "|     xcccccccccccccc|\n" +
            "----------------------\n" +
            "\n" +
            "Please enter your command\n"
        val input = ByteArrayInputStream((
                "C 20 10\n" +
                "L 5 4 5 9\n" +
                "L 0 8 8 8\n" +
                "B 2 2 c\n"
            ).toByteArray())
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val app = Drawing(input, printStream)
        app.run()

        val actualOutput = String(outputStream.toByteArray())

        actualOutput shouldBe expectedOutput
    }
}