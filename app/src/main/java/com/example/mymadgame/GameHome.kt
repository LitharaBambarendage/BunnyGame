package com.example.mymadgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GameHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_home)

        val goBtn: Button = findViewById(R.id.goBtn)

        goBtn.setOnClickListener {
            // Create an Intent to navigate to the next activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Start the next activity
        }
    }
}