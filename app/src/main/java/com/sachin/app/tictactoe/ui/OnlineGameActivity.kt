package com.sachin.app.tictactoe.ui

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.sachin.app.tictactoe.BoardView
import com.sachin.app.tictactoe.R
import com.sachin.app.tictactoe.databinding.ActivityOnlineGameBinding
import com.sachin.app.tictactoe.model.Fillable
import com.sachin.app.tictactoe.model.Room
import com.sachin.app.tictactoe.util.GameUtil
import com.sachin.app.tictactoe.util.addListenerForSingleValueEvent
import com.sachin.app.tictactoe.util.addValueEventListener
import com.sachin.app.tictactoe.util.isAllFilled

class OnlineGameActivity : AppCompatActivity(R.layout.activity_online_game),
    BoardView.OnPointClickListener {
    private val binding by viewBinding(ActivityOnlineGameBinding::bind)
    private val database = FirebaseDatabase.getInstance()
    private var room: Room? = null
    private val roomArgs by navArgs<OnlineGameActivityArgs>()
    private var myTurn = false

    private val roomId: String
        get() = roomArgs.roomId.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val user = getUser()

        database.getReference(roomId).addListenerForSingleValueEvent({
            room = it.getValue(Room::class.java)
            Log.i(TAG, "onCreate: Room: $room")
        })

        database.getReference("$roomId/playerTurn").addValueEventListener({
            val turn = it.getValue(Int::class.java)
            myTurn = turn == if (roomArgs.myRoom) 1 else 2
            Log.i(TAG, "onCreate: Turn: $turn")
        })

        database.getReference("$roomId/game")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                    val fillable = snapshot.getValue(Fillable::class.java)

                    if (fillable != null)
                        binding.boardView.drawFillable(fillable)

                    if (GameUtil.checkWin(binding.boardView.getAllFilledPoints())) {
                        val player =
                            if (fillable?.fill == "x") room?.player1?.name else room?.player2?.name

                        Toast.makeText(
                            applicationContext,
                            "$player won the game!",
                            Toast.LENGTH_SHORT
                        ).show()

                        database.getReference("$roomId/game").setValue(null)

                        binding.boardView.apply {
                            showWinAndReset(
                                GameUtil.getWinPoints(getAllFilledPoints()),
                                1000
                            )
                        }

                        return
                    }

                    if (binding.boardView.getAllFilledPoints().isAllFilled()) {
                        Toast.makeText(applicationContext, "Game draw!", Toast.LENGTH_SHORT)
                            .show()

                        database.getReference("$roomId/game").setValue(null)

                        binding.boardView.apply {
                            showWinAndReset(
                                GameUtil.getWinPoints(getAllFilledPoints()),
                                1000
                            )
                        }
                    }

                    if (myTurn) {
                        database.getReference("$roomId/playerTurn")
                            .setValue(if (roomArgs.myRoom) 2 else 1)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        if (roomArgs.myRoom)
            database.getReference("$roomId/playerTurn").setValue(1)

        binding.boardView.setOnPointClickListener(this)
    }


    override fun onDestroy() {
        if (room != null)
            database.getReference(room!!.id.toString()).setValue(null)
        super.onDestroy()
    }


    companion object {
        private const val TAG = "OnlineGameActivity"
    }

    override fun onPointClick(point: Point, filled: Boolean) {
        Log.d(TAG, "onPointClick: My Turn: $myTurn  Filled: $filled")

        if (filled || !myTurn) return

        database.getReference("$roomId/game/p${point.x}_${point.y}").setValue(
            Fillable(if (roomArgs.myRoom) "x" else "o", point),
        )
    }
}