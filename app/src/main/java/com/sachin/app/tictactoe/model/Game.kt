package com.sachin.app.tictactoe.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Game(
    var p0_0: Fillable? = null,
    var p0_1: Fillable? = null,
    var p0_2: Fillable? = null,
    var p1_0: Fillable? = null,
    var p1_1: Fillable? = null,
    var p1_2: Fillable? = null,
    var p2_0: Fillable? = null,
    var p2_1: Fillable? = null,
    var p2_2: Fillable? = null
) : Parcelable