package com.example.mymadgame

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout: LinearLayout
    lateinit var startBtn: Button
    lateinit var mGameView: GameView
    lateinit var scoreText: TextView
    lateinit var highestScoreTextView: TextView
    lateinit var sharedPreferences: SharedPreferences
    var gameRunning = false // Add a variable to track if the game is running
    var highestScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)

        startBtn = findViewById(R.id.goBtn)
        rootLayout = findViewById(R.id.rootLayout)
        scoreText = findViewById(R.id.score)
        highestScoreTextView = findViewById(R.id.highestScore)

        startBtn.setOnClickListener {
            if (!gameRunning) { // Start a new game only if the game is not already running
                startNewGame()
            } else { // If the game is already running, restart it
                resetGame()
            }
        }

        // Retrieve and display player score
        val score = sharedPreferences.getInt("playerScore", 0)
        scoreText.text = "Score: 0"

        // Retrieve and display highest score
        highestScore = sharedPreferences.getInt("highestScore", 0)
        highestScoreTextView.text = "Highest Score: $highestScore"
    }

    private fun startNewGame() {
        mGameView = GameView(this, this)
        mGameView.setBackgroundResource(R.drawable.gameback2)
        rootLayout.addView(mGameView)
        startBtn.visibility = View.GONE
        scoreText.visibility = View.GONE
        gameRunning = true
    }

    private fun resetGame() {
        rootLayout.removeView(mGameView) // Remove the existing game view
        val score = sharedPreferences.getInt("playerScore", 0)
        scoreText.text = "Score : 0" // Reset score
        mGameView = GameView(this, this) // Create a new game view
        startNewGame() // Start a new game
    }

    override fun closeGame(mScore: Int) {
        scoreText.text = "Score : $mScore"

        // Save player score to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putInt("playerScore", mScore)

        // Update highest score if necessary
        if (mScore > highestScore) {
            highestScore = mScore
            highestScoreTextView.text = "Highest Score: $highestScore"
            editor.putInt("highestScore", highestScore)
        }

        editor.apply()

        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        scoreText.visibility = View.VISIBLE
        gameRunning = false // Update gameRunning flag when the game is closed
    }
}
