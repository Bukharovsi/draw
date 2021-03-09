package com.bukharov.drawing.drawing.pixel

fun Collection<String>.asString() = this.joinToString(separator = "") { it + "\n" }