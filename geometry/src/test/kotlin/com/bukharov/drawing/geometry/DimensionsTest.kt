package com.bukharov.drawing.geometry

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DimensionsTest {

    @Test
    fun `dimensions might not be zero or below`() {
        shouldThrow<DimensionMustBePositive> { Dimensions(0, 1) }
    }

    @Test
    fun `dimension should be transformed to dot`() {
        Dimensions(width = 2, height = 4).toUpperRightCoordinate() shouldBe Point(1, 3)
    }

    @Test
    fun `coordinates might be transformed to dimension`() {
        Point(1, 3).toDimension() shouldBe Dimensions(width = 2, height = 4)
    }

    @Test
    fun `max of dimensions calculated correctly`() {
        maxOfDimensions(
            Dimensions(1, 40),
            Dimensions(30, 8)
        ) shouldBe Dimensions(30, 40)
    }
}