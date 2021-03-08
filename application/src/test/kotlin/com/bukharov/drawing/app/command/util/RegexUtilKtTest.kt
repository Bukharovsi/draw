package com.bukharov.drawing.app.command.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class RegexUtilKtTest {

    @Test
    fun `group as int should return proper value for a group`() {
        val regex = Regex("^B (\\d+) (\\d+) (.)$", RegexOption.IGNORE_CASE)
            .find("B 2 4 c") ?: throw IllegalStateException("string must be valid for regex")

        regex.groupAsIntOrThrow(1) shouldBe 2
        regex.groupAsIntOrThrow(2) shouldBe 4
    }

    @Test
    fun `group as char should return proper value for a group`() {
        val regex = Regex("^B (\\d+) (\\d+) (.)$", RegexOption.IGNORE_CASE)
            .find("B 2 4 c") ?: throw IllegalStateException("string must be valid for regex")

        regex.groupAsCharOrThrow(3) shouldBe 'c'
    }
}