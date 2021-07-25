package com.example.tic_tac_toe

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val emptyCell = ' '

class GameViewModel: ViewModel() {

    companion object {
        var gameGrid = Array(3){ Array(3) {emptyCell} }
        var colorWin = Array(3){ Array(3) {false} }
    }

    private var _grid = MutableLiveData(gameGrid)
    val grid: LiveData<Array<Array<Char>>> = _grid

    private var _helpMessage = MutableLiveData<String>(MainActivity.instance.getString(R.string.x_is_playing))
    val helpMessage: LiveData<String> = _helpMessage

    private var _winColor = MutableLiveData(colorWin)
    val winColor: LiveData<Array<Array<Boolean>>> = _winColor

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
                        MainActivity.instance.getString(R.string.x_wins) + "\n" + MainActivity.instance.getString(R.string.reset_message)
                    }
                    symbWins(gameGrid, 'O') -> {
                        gameOver = true
                        MainActivity.instance.getString(R.string.o_wins) + "\n" + MainActivity.instance.getString(R.string.reset_message)
                    }
                    countSymb(gameGrid, emptyCell) == 0 -> {
                        gameOver = true
                        MainActivity.instance.getString(R.string.draw) + "\n" + MainActivity.instance.getString(R.string.reset_message)
                    }
                    else -> {
                        playingSymbol = if (playingSymbol == 'X') { 'O' } else { 'X' }
                        if (playingSymbol == 'X') {
                            MainActivity.instance.getString(R.string.x_is_playing)
                        } else {
                            MainActivity.instance.getString(R.string.o_is_playing)
                        }
                    }
                }
            } else {
                _helpMessage.value = _helpMessage.value + "\n" + MainActivity.instance.getString(R.string.full_cell)
            }
        }
    }

    private fun symbWins(myGrid: Array<Array<Char>>, symb: Char):Boolean {
        return when {
            myGrid[0][0] == symb && myGrid[0][1] == symb && myGrid[0][2] == symb -> {
                colorWin[0][0] = true
                colorWin[0][1] = true
                colorWin[0][2] = true
                _winColor.value = colorWin
                true
            }
            myGrid[1][0] == symb && myGrid[1][1] == symb && myGrid[1][2] == symb -> {
                colorWin[1][0] = true
                colorWin[1][1] = true
                colorWin[1][2] = true
                _winColor.value = colorWin
                true
            }
            myGrid[2][0] == symb && myGrid[2][1] == symb && myGrid[2][2] == symb -> {
                colorWin[2][0] = true
                colorWin[2][1] = true
                colorWin[2][2] = true
                _winColor.value = colorWin
                true
            }
            myGrid[0][0] == symb && myGrid[1][0] == symb && myGrid[2][0] == symb -> {
                colorWin[0][0] = true
                colorWin[1][0] = true
                colorWin[2][0] = true
                _winColor.value = colorWin
                true
            }
            myGrid[0][1] == symb && myGrid[1][1] == symb && myGrid[2][1] == symb -> {
                colorWin[0][1] = true
                colorWin[1][1] = true
                colorWin[2][1] = true
                _winColor.value = colorWin
                true
            }
            myGrid[0][2] == symb && myGrid[1][2] == symb && myGrid[2][2] == symb -> {
                colorWin[0][2] = true
                colorWin[1][2] = true
                colorWin[2][2] = true
                _winColor.value = colorWin
                true
            }
            myGrid[0][0] == symb && myGrid[1][1] == symb && myGrid[2][2] == symb -> {
                colorWin[0][0] = true
                colorWin[1][1] = true
                colorWin[2][2] = true
                _winColor.value = colorWin
                true
            }
            myGrid[0][2] == symb && myGrid[1][1] == symb && myGrid[2][0] == symb -> {
                colorWin[0][2] = true
                colorWin[1][1] = true
                colorWin[2][0] = true
                _winColor.value = colorWin
                true
            }
            else -> false
        }
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
                    colorWin[i][j] = false
                }
            }
            _grid.value = gameGrid
            _winColor.value = colorWin
            _helpMessage.value = MainActivity.instance.getString(R.string.x_is_playing)
            playingSymbol = 'X'
            gameOver = false
        }
        return false
    }

}
