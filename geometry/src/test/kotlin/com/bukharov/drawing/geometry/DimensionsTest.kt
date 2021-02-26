package com.bukharov.drawing.geometry

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DimensionsTest {

    @Test
    fun `dimension should be transformed to dot`() {
        Dimensions(width = 2, height = 4).toUpperRightCoordinate() shouldBe Point(1, 3)
    }

    @Test
    fun `coordinates might be transformed to dimension`() {
        Point(1, 3).toDimension() shouldBe Dimensions(width = 2, height = 4)
    }
}