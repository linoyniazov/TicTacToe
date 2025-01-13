package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val board = Array(3) { CharArray(3) { ' ' } } // לוח המשחק
    private var currentPlayer = 'X' // השחקן הנוכחי
    private lateinit var winnerTextView: TextView // ה-TextView להודעת הניצחון
    private lateinit var playAgainButton: Button // כפתור "Play Again"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        winnerTextView = findViewById(R.id.winnerTextView)
        playAgainButton = findViewById(R.id.resetButton)

        // הגדרת פעולות ללחיצה על כפתורי הלוח
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            val row = i / 3
            val col = i % 3

            button.setOnClickListener {
                if (button.text.isEmpty()) {
                    button.text = currentPlayer.toString()
                    board[row][col] = currentPlayer

                    if (checkWinner()) {
                        showWinner() // הצגת הודעת הניצחון
                        disableBoard(gridLayout)
                    } else if (isBoardFull()) {
                        Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
                    } else {
                        currentPlayer = if (currentPlayer == 'X') 'O' else 'X' // החלפת תור
                    }
                }
            }
        }

        // הגדרת כפתור Play Again
        playAgainButton.setOnClickListener {
            resetBoard(gridLayout)
        }
    }

    private fun showWinner() {
        winnerTextView.text = "$currentPlayer Wins!"
        winnerTextView.visibility = View.VISIBLE
    }

    private fun resetBoard(gridLayout: GridLayout) {
        currentPlayer = 'X'
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
            button.isEnabled = true
        }
        for (row in board) {
            row.fill(' ')
        }
        winnerTextView.visibility = View.GONE // הסתרת הודעת הניצחון
    }

    private fun disableBoard(gridLayout: GridLayout) {
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.isEnabled = false
        }
    }

    private fun checkWinner(): Boolean {
        // בדיקה של שורות, עמודות ואלכסונים
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            if (row.contains(' ')) return false
        }
        return true
    }
}
