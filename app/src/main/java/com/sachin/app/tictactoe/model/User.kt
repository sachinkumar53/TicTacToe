package com.sachin.app.tictactoe.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class User(
    var name: String? = null,
    var id: String? = null
) : Parcelable
