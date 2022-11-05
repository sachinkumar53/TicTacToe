package com.sachin.app.tictactoe.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sachin.app.tictactoe.R
import com.sachin.app.tictactoe.databinding.FragmentJoinRoomBinding
import com.sachin.app.tictactoe.model.Room


class JoinRoomFragment : Fragment(R.layout.fragment_join_room) {
    private val binding by viewBinding(FragmentJoinRoomBinding::bind)
    private val database = FirebaseDatabase.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            createRoom.setOnClickListener {
                val roomId = (10000..99999).random()
                findNavController().navigate(
                    JoinRoomFragmentDirections.actionJoinRoomFragment2ToCreateRoomFragment(
                        roomId
                    )
                )
            }

            joinRoom.setOnClickListener {
                if (roomEditText.text.isNullOrBlank() || roomEditText.text.length != 5) {
                    errorRoomCode()
                } else {
                    database.getReference(roomEditText.text.trim().toString())
                        .addValueEventListener(
                            object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val room = snapshot.getValue(Room::class.java)
                                    if (room == null) {
                                        errorRoomCode()
                                    } else {
                                        snapshot.ref.removeEventListener(this)
                                        findNavController().navigate(
                                            JoinRoomFragmentDirections.actionJoinRoomFragmentToOnlineGameActivity(
                                                room.id, false
                                            )
                                        )
                                        snapshot.ref.updateChildren(mapOf("player2" to context?.getUser()))
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }
                            }
                        )
                }
            }
        }
    }

    private fun errorRoomCode() =
        Toast.makeText(context, "Room code is not valid", Toast.LENGTH_SHORT).show()
}