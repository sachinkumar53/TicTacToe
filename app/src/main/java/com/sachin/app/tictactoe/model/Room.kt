package com.sachin.app.tictactoe.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Room(
    var player1: User? = null,
    var player2: User? = null,
    var id: Int = 0,
    var playerTurn: Int = 0,
    var game: Game? = null,
    var winner: Int = 0
) : Parcelable