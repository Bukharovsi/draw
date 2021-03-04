package com.bukharov.drawing.command.drawing

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
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
}