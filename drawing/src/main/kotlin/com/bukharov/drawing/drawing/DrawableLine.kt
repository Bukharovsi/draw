package com.bukharov.drawing.drawing

//class DrawableHorizontalLine (
//    val line: Line
//){
//    fun draw(): PixelLayer {
//        val constY = line.a.y
//        val layer = PixelLayer(line.downLeftCorner(), line.upperRightCorner())
//        (line.downLeftCorner().x..line.upperRightCorner().x)
//            .forEach { x -> layer[x][constY] = Pixel.X }
//        return layer
//    }
//}