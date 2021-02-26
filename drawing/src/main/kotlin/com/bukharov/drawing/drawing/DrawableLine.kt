package com.bukharov.drawing.drawing

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import arrow.core.left
import arrow.core.right
import com.bukharov.drawing.drawing.pixel.Pixel
import com.bukharov.drawing.drawing.pixel.PixelLayer
import com.bukharov.drawing.geometry.DrawingError
import com.bukharov.drawing.geometry.Line
import com.bukharov.drawing.geometry.Point

class DrawableLine(
    private val line: Line
) {

    fun rasterize(): Either<DrawingError,PixelLayer> =
        tupled(
            PixelLayer.create(
                width = line.upperRightCorner().x + 1,
                height = line.upperRightCorner().y + 1
            ),
            when (true) {
                line.isHorizontal() -> horizontalDots(line).right()
                line.isVertical() -> verticalDots(line).right()
                else -> LineTypeIsNotSupported.left()
            }
        ).map { tupled ->
                tupled.b.forEach { point -> tupled.a.change(point, Pixel.X) }
                tupled.a
        }
}

private val horizontalDots: (Line) -> Set<Point> = { line->
    (line.a.x..line.b.x)
        .map { currentX -> Point(x = currentX, y = line.a.y) }
        .toSet()
}

private val verticalDots: (Line) -> Set<Point> = { line->
    (line.a.y..line.b.y)
        .map { currentY -> Point(x = line.a.x, y = currentY) }
        .toSet()
}

object LineTypeIsNotSupported: DrawingError