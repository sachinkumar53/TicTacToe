package com.sachin.app.tictactoe.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sachin.app.tictactoe.R
import com.sachin.app.tictactoe.RoomActivity
import com.sachin.app.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = getUser()
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            binding.userName.text = user.name
        }

        with(binding) {
            playWithFriend.setOnClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        GameActivity::class.java
                    )
                )
            }
            playWithOnlineFriend.setOnClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        RoomActivity::class.java
                    )
                )
            }

            exit.setOnClickListener { finish() }
        }
    }
}