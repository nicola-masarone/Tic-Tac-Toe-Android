package com.example.tic_tac_toe

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val emptyCell = ' '

class GameViewModel: ViewModel() {

    companion object {
        var gameGrid = Array(3){ Array(3) {emptyCell} }
    }
    private var _grid = MutableLiveData(gameGrid)!!
    val grid: LiveData<Array<Array<Char>>> = _grid

    private var _helpMessage = MutableLiveData<String>(MainActivity.instance.getString(R.string.x_is_playing))
    val helpMessage: MutableLiveData<String> = _helpMessage

    private var _winVertLine1Vis = MutableLiveData<Boolean>(false)
    val winVertLine1Vis: LiveData<Boolean> = _winVertLine1Vis
    private var _winVertLine2Vis = MutableLiveData<Boolean>(false)
    val winVertLine2Vis: LiveData<Boolean> = _winVertLine2Vis
    private var _winVertLine3Vis = MutableLiveData<Boolean>(false)
    val winVertLine3Vis: LiveData<Boolean> = _winVertLine3Vis
    private var _winHorLine1Vis = MutableLiveData<Boolean>(false)
    val winHorLine1Vis: LiveData<Boolean> = _winHorLine1Vis
    private var _winHorLine2Vis = MutableLiveData<Boolean>(false)
    val winHorLine2Vis: LiveData<Boolean> = _winHorLine2Vis
    private var _winHorLine3Vis = MutableLiveData<Boolean>(false)
    val winHorLine3Vis: LiveData<Boolean> = _winHorLine3Vis
    private var _winDiagLine1Vis = MutableLiveData<Boolean>(false)
    val winDiagLine1Vis: LiveData<Boolean> = _winDiagLine1Vis
    private var _winDiagLine2Vis = MutableLiveData<Boolean>(false)
    val winDiagLine2Vis: LiveData<Boolean> = _winDiagLine2Vis
    private var winnerLine: MutableLiveData<Boolean> = _winVertLine1Vis


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
                        playingSymbol = if (playingSymbol == 'X') {
                            'O'
                        } else {
                            'X'
                        }
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
                _winHorLine1Vis.value = true
                winnerLine = _winHorLine1Vis
                true
            }
            myGrid[1][0] == symb && myGrid[1][1] == symb && myGrid[1][2] == symb -> {
                _winHorLine2Vis.value = true
                winnerLine = _winHorLine2Vis
                true
            }
            myGrid[2][0] == symb && myGrid[2][1] == symb && myGrid[2][2] == symb -> {
                _winHorLine3Vis.value = true
                winnerLine = _winHorLine3Vis
                true
            }
            myGrid[0][0] == symb && myGrid[1][0] == symb && myGrid[2][0] == symb -> {
                _winVertLine1Vis.value = true
                winnerLine = _winVertLine1Vis
                true
            }
            myGrid[0][1] == symb && myGrid[1][1] == symb && myGrid[2][1] == symb -> {
                _winVertLine2Vis.value = true
                winnerLine = _winVertLine2Vis
                true
            }
            myGrid[0][2] == symb && myGrid[1][2] == symb && myGrid[2][2] == symb -> {
                _winVertLine3Vis.value = true
                winnerLine = _winVertLine3Vis
                true
            }
            myGrid[0][0] == symb && myGrid[1][1] == symb && myGrid[2][2] == symb -> {
                _winDiagLine1Vis.value = true
                winnerLine = _winDiagLine1Vis
                true
            }
            myGrid[0][2] == symb && myGrid[1][1] == symb && myGrid[2][0] == symb -> {
                _winDiagLine2Vis.value = true
                winnerLine = _winDiagLine2Vis
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
                }
            }
            _grid.postValue(gameGrid)
            winnerLine.value = false
            playingSymbol = 'X'
            _helpMessage.value = MainActivity.instance.getString(R.string.x_is_playing)
            gameOver = false
        }
        return false
    }

}
