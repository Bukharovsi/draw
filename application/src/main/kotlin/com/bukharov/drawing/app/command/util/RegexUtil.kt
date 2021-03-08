package com.bukharov.drawing.app.command.util

fun MatchResult.groupAsIntOrThrow(group: Int) =
    this.groups[group]?.value?.toInt()
    ?: throw NumberFormatException("int expected, but ${this.groups[group]?.value} given")

fun MatchResult.groupAsCharOrThrow(group: Int) =
    this.groups[group]?.value?.toCharArray()?.first()
        ?: throw IllegalArgumentException("Illegal char given")