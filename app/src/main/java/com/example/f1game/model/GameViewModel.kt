package com.example.f1game.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import com.example.f1game.GameUiState
import com.example.f1game.data.MAX_NO_OF_WORDS
import com.example.f1game.data.POINTS_FOR_ANSWER
import com.example.f1game.data.drivers
import com.example.f1game.data.listOfDrivers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class GameViewModel : ViewModel() {
    private val _uistate = MutableStateFlow(GameUiState())
    var uistate : StateFlow<GameUiState> = _uistate.asStateFlow()

    private var usedDrivers : MutableSet<drivers> = mutableSetOf()
    lateinit var currentWord : String
    var userGuess by mutableStateOf("")
        private set

    private fun pickDrivers() : drivers{
        val driv = listOfDrivers.random()
        currentWord = driv.nameDrivers
        if(!usedDrivers.contains(driv)){
            usedDrivers.add(driv)
            return driv
        }
        else{
            return pickDrivers()
        }
    }

    public fun userGuessing(guessedWord : String){
        userGuess = guessedWord
    }

    public fun checkUserGuess(){
        if(userGuess.lowercase().equals(currentWord)){
            val updatedScore = _uistate.value.score.plus(POINTS_FOR_ANSWER)
            updateGameState(updatedScore)
        }
        else{
            _uistate.update {
                currentState -> currentState.copy(
                    isGuessedWrong = true
                )
            }
        }
        userGuessing("")
    }

    private fun updateGameState(updatedScore : Int){
        if(_uistate.value.wordCount == MAX_NO_OF_WORDS){
            _uistate.update {
                currentstate -> currentstate.copy(
                    isGameOver = true,
                    score = updatedScore,
                    isGuessedWrong = false,
                    giveHint = false
                )
            }
        }
        else{
            _uistate.update {
                currentstate -> currentstate.copy(
                    driver1 = pickDrivers(),
                    score = updatedScore,
                    wordCount = _uistate.value.wordCount.inc(),
                    isGuessedWrong = false,
                    giveHint = false
                )
            }
        }
    }
    fun showHint(){
        _uistate.update {
            currentState -> currentState.copy(
                giveHint = !(_uistate.value.giveHint)
            )
        }
    }

    fun resetGame(){
        usedDrivers.clear()
        _uistate.value = GameUiState(driver1 = pickDrivers(),score = 0)
    }

    fun skipGame(){
        updateGameState(_uistate.value.score)
        userGuessing("")
    }

    fun guidelineWindowChange(){
        _uistate.update {
            currentState -> currentState.copy(
                guidelineShow = !(_uistate.value.guidelineShow)
            )
        }
    }
    init {
        resetGame()
    }
}