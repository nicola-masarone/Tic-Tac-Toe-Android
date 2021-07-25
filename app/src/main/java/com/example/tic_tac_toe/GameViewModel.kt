package com.example.tic_tac_toe

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val emptyCell = ' '
const val xIsPlaying = "X is playing"
const val oIsPlaying = "O is playing"
const val fullCell = "Cell is full!"
const val xWins = "X wins!"
const val oWins = "O wins!"
const val drawGame = "Draw"
const val resetMsg = "Long click on this message to reset game"

class GameViewModel: ViewModel() {

    companion object {
        var gameGrid = Array(3){ Array(3) {emptyCell} }
    }
    private var _grid = MutableLiveData(gameGrid)!!
    val grid: LiveData<Array<Array<Char>>> = _grid
    private var _helpMessage = MutableLiveData<String>(xIsPlaying)
    val helpMessage: MutableLiveData<String> = _helpMessage

    private var playingSymbol = 'X'
    private var gameOver = false

    fun setCellContent(row: Int, col: Int) {
        if (!gameOver) {
            if (gameGrid[row][col] == emptyCell) {
                gameGrid[row][col] = playingSymbol
                _grid.postValue(gameGrid)
                _helpMessage.value = when {
                    symbWins(gameGrid, 'X') -> {
                        gameOver = true
                        xWins + "\n" + resetMsg
                    }
                    symbWins(gameGrid, 'O') -> {
                        gameOver = true
                        oWins + "\n" + resetMsg
                    }
                    countSymb(gameGrid, emptyCell) == 0 -> {
                        gameOver = true
                        drawGame + "\n" + resetMsg
                    }
                    else -> {
                        playingSymbol = if (playingSymbol == 'X') {
                            'O'
                        } else {
                            'X'
                        }
                        if (playingSymbol == 'X') {
                            xIsPlaying
                        } else {
                            oIsPlaying
                        }
                    }
                }
            } else {
                _helpMessage.value = _helpMessage.value + "\n" + fullCell
            }
        }
    }

    private fun symbWins(myGrid: Array<Array<Char>>, symb: Char):Boolean {
        return  myGrid[0][0] == symb && myGrid[0][1] == symb && myGrid[0][2] == symb ||
                myGrid[1][0] == symb && myGrid[1][1] == symb && myGrid[1][2] == symb ||
                myGrid[2][0] == symb && myGrid[2][1] == symb && myGrid[2][2] == symb ||
                myGrid[0][0] == symb && myGrid[1][0] == symb && myGrid[2][0] == symb ||
                myGrid[0][1] == symb && myGrid[1][1] == symb && myGrid[2][1] == symb ||
                myGrid[0][2] == symb && myGrid[1][2] == symb && myGrid[2][2] == symb ||
                myGrid[0][0] == symb && myGrid[1][1] == symb && myGrid[2][2] == symb ||
                myGrid[0][2] == symb && myGrid[1][1] == symb && myGrid[2][0] == symb
    }

    private fun countSymb(myGrid: Array<Array<Char>>, symb: Char): Int {
        var counter = 0
        for (i in 0..2) {
            for (j in 0..2) {
                if (myGrid[i][j] == symb) { counter++ }
            }
        }
        return counter
    }

    fun resetGame(view: View): Boolean {
        if (gameOver) {
            for (i in 0..2) {
                for (j in 0..2) {
                    gameGrid[i][j] = emptyCell
                }
            }
            _grid.postValue(gameGrid)
            playingSymbol = 'X'
            _helpMessage.value = xIsPlaying
            gameOver = false
        }
        return false
    }

}
