package com.bukharov.drawing.drawing.pixel

import com.bukharov.drawing.geometry.Dimensions

interface Printable {
    val dimensions: Dimensions
    fun asStrings(): List<String>
}