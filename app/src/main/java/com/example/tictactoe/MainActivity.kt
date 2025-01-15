package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val board = Array(3) { CharArray(3) { ' ' } }
    private var currentPlayer = 'X'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        val turnTextView = findViewById<TextView>(R.id.turnTextView)
        updateTurnText(turnTextView)

        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            val row = i / 3
            val col = i % 3

            button.setOnClickListener {
                if (button.text.isEmpty()) {
                    println("Player $currentPlayer clicked row $row, column $col")
                    button.text = currentPlayer.toString()
                    board[row][col] = currentPlayer

                    if (checkWinner()) {
                        println("Player $currentPlayer is the winner")
                        turnTextView.text = getString(R.string.winner_text, currentPlayer)
                        disableBoard(gridLayout)
                    } else if (isBoardFull()) {
                        println("The game is a draw")
                        turnTextView.text = getString(R.string.draw_text)
                    } else {
                        switchPlayer()
                        updateTurnText(turnTextView)
                    }
                }
            }
        }

        val resetButton = findViewById<Button>(R.id.resetButton)
        resetButton.setOnClickListener {
            resetBoard(gridLayout)
            currentPlayer = 'X'
            updateTurnText(turnTextView)
            println("Board reset. Starting with X") // לוג לאיפוס
        }
    }

    private fun updateTurnText(turnTextView: TextView) {
        turnTextView.text = getString(R.string.turn_text, currentPlayer.toString())
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
    }

    private fun resetBoard(gridLayout: GridLayout) {
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
            button.isEnabled = true
        }
        for (row in board) {
            row.fill(' ')
        }
    }

    private fun disableBoard(gridLayout: GridLayout) {
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.isEnabled = false
        }
    }

    private fun checkWinner(): Boolean {
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                println("Row $i wins for player $currentPlayer")
                return true
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                println("Column $i wins for player $currentPlayer")
                return true
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            println("Diagonal \\ wins for player $currentPlayer")
            return true
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            println("Diagonal / wins for player $currentPlayer")
            return true
        }
        println("No winner yet")
        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            if (row.contains(' ')) return false
        }
        return true
    }
}
