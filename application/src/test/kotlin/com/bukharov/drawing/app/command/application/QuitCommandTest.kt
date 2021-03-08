package com.bukharov.drawing.app.command.application

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class QuitCommandTest {

    @ParameterizedTest
    @ValueSource(strings = ["C", "q 23", "QQ", ":wq"])
    fun `command should not be created if string command is NOT correct`(
        stringCommand: String
    ) {
        QuitCommand.Factory().tryToCreate(stringCommand).shouldBeNull()
    }

    @ParameterizedTest
    @ValueSource(strings = ["Q", "q"])
    fun `command should be created if string command is correct`(
        stringCommand: String
    ) {
        QuitCommand.Factory().tryToCreate(stringCommand).shouldBeInstanceOf<QuitCommand>()
    }
}