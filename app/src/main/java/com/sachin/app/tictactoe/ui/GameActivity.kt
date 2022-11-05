package com.sachin.app.tictactoe.ui

import android.graphics.Point
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sachin.app.tictactoe.BoardView
import com.sachin.app.tictactoe.R
import com.sachin.app.tictactoe.databinding.ActivityGameBinding
import com.sachin.app.tictactoe.util.GameUtil
import com.sachin.app.tictactoe.util.isAllFilled

class GameActivity : AppCompatActivity(R.layout.activity_game),
    BoardView.OnPointClickListener {
    private var myTurn = true
    private val binding by viewBinding(ActivityGameBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.boardView.setOnPointClickListener(this)
    }

    override fun onPointClick(point: Point, filled: Boolean) {
        if (filled) {
            Toast.makeText(this, "Filled", Toast.LENGTH_SHORT).show()
            return
        }


        with(binding) {
            if (myTurn) boardView.drawX(point)
            else boardView.drawO(point)

            if (GameUtil.checkWin(boardView.getAllFilledPoints())) {
                val player = if (myTurn) "Player 1" else "Player 2"
                Toast.makeText(this@GameActivity, "Game won by $player", Toast.LENGTH_SHORT).show()
                boardView.apply {
                    isEnabled = false
                    showWinAndReset(GameUtil.getWinPoints(boardView.getAllFilledPoints()), 2000)
                    postDelayed({
                        isEnabled = true
                        myTurn = true
                    }, 2000)
                }
                return
            }

            if (boardView.getAllFilledPoints().isAllFilled()) {
                Toast.makeText(this@GameActivity, "Game draw!", Toast.LENGTH_SHORT).show()
                boardView.isEnabled = false
                boardView.showWinAndReset(
                    GameUtil.getWinPoints(boardView.getAllFilledPoints()),
                    2000
                )
                boardView.postDelayed({
                    boardView.isEnabled = true
                    myTurn = true
                }, 2000)
                return
            }
            myTurn = !myTurn
        }


    }

}