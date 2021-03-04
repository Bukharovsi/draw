package com.bukharov.drawing.command

import com.bukharov.drawing.command.application.QuitCommand
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test

internal class CommandFactoryTest {

    @Test
    fun `should return existing command if string command is supported`() {
        CommandFactory.createCommand("Q").shouldBeInstanceOf<QuitCommand>()
    }

    @Test
    fun `should NOT return existing command if string command is NOT supported`() {
        CommandFactory.createCommand("Q 23").shouldBeNull()
    }
}