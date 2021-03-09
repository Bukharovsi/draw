package com.bukharov.drawing

import com.bukharov.drawing.app.Drawing

fun main() {
    Drawing(
        inStream = System.`in`,
        outStream = System.out
    ).run()
}
