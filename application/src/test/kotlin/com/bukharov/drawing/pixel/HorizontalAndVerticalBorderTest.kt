package com.bukharov.drawing.pixel

import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Dimensions
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldStartWith
import org.junit.jupiter.api.Test

internal class HorizontalAndVerticalBorderTest {

    @Test
    fun `border should be around pixel layer`() {
        val layerWithBorder = HorizontalAndVerticalBorder(
            PixelLayer.create(Dimensions(5, 5))
        ).asStrings()

        layerWithBorder.size shouldBe 5 + 2
        layerWithBorder.first() shouldBe "-------"
        (1..5).forEach { i ->
            layerWithBorder[i] shouldStartWith "|"
            layerWithBorder[i] shouldEndWith "|"
        }
        layerWithBorder.last() shouldBe "-------"
    }
}