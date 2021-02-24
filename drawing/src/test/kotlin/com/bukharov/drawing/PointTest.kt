package com.bukharov.drawing

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class PointTest {

    @Test
    fun `a point should be equal to another point if coordinates are the same`() {
        Point(5, 6) shouldBe  Point(5, 6)
    }

    @Test
    fun `a point should not be equal to another point if coordinates are NOT the same`() {
        Point.zero shouldNotBe Point(5, 6)
    }

    @Test
    fun `point should be more by any direction`() {
        Point(3, 5).moreByAnyDirectionThan(Point( 1, 2)) shouldBe true
    }

    @Test
    fun `point should NOT be more by any direction`() {
        Point(1, 1).moreByAnyDirectionThan(Point( 3, 1)) shouldBe false
    }

    @Test
    fun `point should be less by any direction`() {
        Point(-1, 0).lessByAnyDirectionThan(Point( 2, 2)) shouldBe true
    }

    @Test
    fun `point should NOT be less by any direction`() {
        Point(22, 3).lessByAnyDirectionThan(Point( 2, 2)) shouldBe false
    }
}