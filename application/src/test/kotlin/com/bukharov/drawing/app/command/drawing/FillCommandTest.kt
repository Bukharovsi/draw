package com.bukharov.drawing.app.command.drawing

import com.bukharov.drawing.drawing.DrawableCanvas
import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.geometry.Dimensions
import com.bukharov.drawing.geometry.Point
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class FillCommandTest {

    @ParameterizedTest
    @ValueSource(strings = ["", "B", "B 20", "B 34 5 xf"])
    fun `command should not be created if string command is NOT correct`(
        stringCommand: String
    ) {
        FillCommand.Factory().tryToCreate(stringCommand).shouldBeNull()
    }

    @ParameterizedTest
    @ValueSource(strings = ["B 20 5 c", "b 1 1 V", "B 8 2222222 _"])
    fun `command should be created if string command is correct`(
        stringCommand: String
    ) {
        FillCommand.Factory().tryToCreate(stringCommand).shouldBeInstanceOf<FillCommand>()
    }

    @Test
    fun `command should fill canvas`() {
        val canvas = DrawableCanvas(Dimensions(5, 5))
        FillCommand.Factory().tryToCreate("B 0 0 c")
            ?.execute(canvas)
            ?.rasterize()
            .should {
                it?.get(Point.zero) shouldBe Pixel.FiledPixel('c')
            }
    }
}