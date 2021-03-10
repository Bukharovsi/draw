package com.bukharov.drawing.app.command.drawing.error

import com.bukharov.drawing.geometry.UserReadableError

class CanvasShouldBePresent : UserReadableError, IllegalStateException() {
    override fun message() = "To perform the operation canvas must be created"
}