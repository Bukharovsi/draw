package com.bukharov.drawing.drawing

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Line
import com.bukharov.drawing.geometry.Point

class DrawableLine(
    private val line: Line
) {

    fun rasterize(): PixelLayer {
        val pixelLayer = PixelLayer.create(
            width = line.upperRightCorner().x + 1,
            height = line.upperRightCorner().y + 1
        )

        when (true) {
            line.isHorizontal() -> horizontalDots(line)
            line.isVertical() -> verticalDots(line)
            else -> throw LineTypeIsNotSupported
        }.forEach { point -> pixelLayer.change(point, Pixel.X) }

        return pixelLayer
    }
}

private val horizontalDots: (Line) -> Set<Point> = { line ->
    (line.a.x..line.b.x)
        .map { currentX -> Point(x = currentX, y = line.a.y) }
        .toSet()
}

private val verticalDots: (Line) -> Set<Point> = { line ->
    (line.a.y..line.b.y)
        .map { currentY -> Point(x = line.a.x, y = currentY) }
        .toSet()
}

object LineTypeIsNotSupported : IllegalStateException()