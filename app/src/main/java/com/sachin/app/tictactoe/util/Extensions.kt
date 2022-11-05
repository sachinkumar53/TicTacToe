package com.sachin.app.tictactoe.util

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

inline fun DatabaseReference.addValueEventListener(
    crossinline onDataChange: (snapshot: DataSnapshot) -> Unit,
    crossinline onCancelled: (error: DatabaseError) -> Unit = {}
) {

    addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            onDataChange.invoke(snapshot)
        }

        override fun onCancelled(error: DatabaseError) {
            onCancelled.invoke(error)
        }
    })
}

inline fun DatabaseReference.addListenerForSingleValueEvent(
    crossinline onDataChange: (snapshot: DataSnapshot) -> Unit,
    crossinline onCancelled: (error: DatabaseError) -> Unit = {}
) {

    addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            onDataChange.invoke(snapshot)
        }

        override fun onCancelled(error: DatabaseError) {
            onCancelled.invoke(error)
        }
    })
}