package com.example.f1game

import androidx.compose.runtime.mutableStateOf
import com.example.f1game.data.drivers

data class GameUiState(
    val driver1 : drivers = drivers(0,"",0),
    val score : Int = 0,
    val isGuessedWrong : Boolean = false,
    val isGameOver : Boolean = false,
    val wordCount : Int = 1,
    val giveHint : Boolean = false,
    val guidelineShow : Boolean = false
)
