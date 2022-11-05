package com.sachin.app.tictactoe.model

import android.graphics.Point
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Fillable(val fill: String = "x", val x: Int = 0, val y: Int = 0) : Parcelable {
    constructor(fill: String = "x", point: Point = Point(0, 0)) : this(fill, point.x, point.y)
}