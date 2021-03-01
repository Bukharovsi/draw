package com.bukharov.drawing.drawing.drawable

import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.Line
import com.bukharov.drawing.geometry.Point
import com.bukharov.drawing.geometry.toDimension
import kotlin.math.max
import kotlin.math.min

class DrawableLine(
    private val line: Line
) : Drawable {

    override fun rasterize(): PixelLayer {
        val pixelLayer = PixelLayer.create(
            line.upperRightCorner().toDimension()
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
    (min(line.a.x, line.b.x)..max(line.a.x, line.b.x))
        .map { currentX -> Point(x = currentX, y = line.a.y) }
        .toSet()
}

private val verticalDots: (Line) -> Set<Point> = { line ->
    (min(line.a.y, line.b.y)..max(line.a.y, line.b.y))
        .map { currentY -> Point(x = line.a.x, y = currentY) }
        .toSet()
}

object LineTypeIsNotSupported : IllegalStateException()