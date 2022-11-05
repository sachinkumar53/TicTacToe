package com.sachin.app.tictactoe.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.database.FirebaseDatabase
import com.sachin.app.tictactoe.R
import com.sachin.app.tictactoe.databinding.ActivityLoginBinding
import com.sachin.app.tictactoe.model.User

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private val binding by viewBinding(ActivityLoginBinding::bind)
    private val database = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            button.setOnClickListener {
                if (userName.text.isNullOrBlank()) {
                    Toast.makeText(applicationContext, "Enter your name", Toast.LENGTH_SHORT).show()
                } else {
                    val id = database.push().key!!
                    val user = User(userName.text.trim().toString(), id)
                    saveUser(user)
                    database.child(id).setValue(user).addOnCompleteListener {
                        finish()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}


fun Context.saveUser(user: User) =
    getSharedPreferences("settings", Context.MODE_PRIVATE).edit().apply {
        putString("name", user.name)
        putString("id", user.id)
    }.apply()

fun Context.getUser(): User? {
    val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
    val name = prefs.getString("name", null)
    val id = prefs.getString("id", null)
    return if (name == null || id == null)
        null
    else User(name, id)
}