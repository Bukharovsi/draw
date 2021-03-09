package com.bukharov.drawing.app.command.drawing

import com.bukharov.drawing.drawing.DrawableCanvas
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.Rectangle
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class DrawRectangleCommandTest {

    @ParameterizedTest
    @ValueSource(strings = ["", "R", "R 20", "R 34 5", "R 34 5 4 5 x", "L 1 1 1 1"])
    fun `command should not be created if string command is NOT correct`(
        stringCommand: String
    ) {
        DrawRectangleCommand.Factory().tryToCreate(stringCommand).shouldBeNull()
    }

    @ParameterizedTest
    @ValueSource(strings = ["R 20 5 4 5", "r 1 1 1 1", "R 8 2222222 16 73"])
    fun `command should be created if string command is correct`(
        stringCommand: String
    ) {
        DrawRectangleCommand.Factory().tryToCreate(stringCommand).shouldBeInstanceOf<DrawRectangleCommand>()
    }

    @Test
    fun `command should create rectangle with correct shape`() {
        val command = DrawRectangleCommand.Factory().tryToCreate("R 2 5 5 0")
        val canvas = DrawableCanvas(Dimensions(10, 10))
        command?.execute(canvas)
        canvas.shapes() shouldContain Rectangle(upperLeftCorner = Point(2, 5), lowerRightCorner = Point(5, 0))
    }
}