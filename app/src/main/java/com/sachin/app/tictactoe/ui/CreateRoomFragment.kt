package com.sachin.app.tictactoe.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sachin.app.tictactoe.R
import com.sachin.app.tictactoe.databinding.FragmentCreateRoomBinding
import com.sachin.app.tictactoe.model.Room


class CreateRoomFragment : Fragment(R.layout.fragment_create_room) {
    private val binding by viewBinding(FragmentCreateRoomBinding::bind)
    private val database = FirebaseDatabase.getInstance()
    private val roomArgs by navArgs<CreateRoomFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        database.getReference(roomArgs.roomId.toString()).setValue(
            Room(
                player1 = context.getUser(),
                id = roomArgs.roomId
            )
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            database.getReference(roomArgs.roomId.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val room = snapshot.getValue(Room::class.java)
                        roomCode.text = room?.id?.toString()
                        player1.text = room?.player1?.name
                        player2.text = room?.player2?.name

                        if (room?.player1 != null && room.player2 != null) {
                            snapshot.ref.removeEventListener(this)
                            findNavController().navigate(
                                CreateRoomFragmentDirections.actionCreateRoomFragmentToOnlineGameActivity(
                                    room.id, true
                                )
                            )
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

        }
    }
}