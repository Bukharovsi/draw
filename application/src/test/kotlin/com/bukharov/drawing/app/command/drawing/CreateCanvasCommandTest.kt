package com.bukharov.drawing.app.command.drawing

import com.bukharov.drawing.geometry.Dimensions
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class CreateCanvasCommandTest {

    @ParameterizedTest
    @ValueSource(strings = ["", "C", "C 20", "C 34 5 x"])
    fun `command should not be created if string command is NOT correct`(
        stringCommand: String
    ) {
        CreateCanvasCommand.Factory().tryToCreate(stringCommand).shouldBeNull()
    }

    @ParameterizedTest
    @ValueSource(strings = ["C 20 5", "c 1 1", "C 8 2222222"])
    fun `command should be created if string command is correct`(
        stringCommand: String
    ) {
        CreateCanvasCommand.Factory().tryToCreate(stringCommand).shouldBeInstanceOf<CreateCanvasCommand>()
    }

    @Test
    fun `command should create canvas`() {
        CreateCanvasCommand.Factory().tryToCreate("C 20 5")
            ?.execute(null)
            .should {
                it.shouldNotBeNull()
                it.size shouldBe Dimensions(20, 5)
            }
    }
}