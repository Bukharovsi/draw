package com.bukharov.drawing.app.command.drawing

import com.bukharov.drawing.drawing.DrawableCanvas
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Line
import com.bukharov.drawing.geometry.Point
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class DrawLineCommandTest {

    @ParameterizedTest
    @ValueSource(strings = ["", "L", "L 20", "L 34 5", "L 34 5 4 5 x", "C 1 1 1 1"])
    fun `command should not be created if string command is NOT correct`(
        stringCommand: String
    ) {
        DrawLineCommand.Factory().tryToCreate(stringCommand).shouldBeNull()
    }

    @ParameterizedTest
    @ValueSource(strings = ["L 20 5 4 5", "l 1 1 1 1", "L 8 2222222 16 73"])
    fun `command should be created if string command is correct`(
        stringCommand: String
    ) {
        DrawLineCommand.Factory().tryToCreate(stringCommand).shouldBeInstanceOf<DrawLineCommand>()
    }

    @Test
    fun `command should create put line on canvas`() {
        val command = DrawLineCommand.Factory().tryToCreate("L 2 5 2 0")
        val canvas = DrawableCanvas(Dimensions(10, 10))
        command?.execute(canvas)
        canvas.shapes() shouldContain Line.create(Point(2, 5), Point(2, 0))
    }
}