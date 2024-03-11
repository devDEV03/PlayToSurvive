package com.example.f1game

import android.annotation.SuppressLint
import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.K
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1game.model.GameViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun GameScreen(gameViewModel : GameViewModel = viewModel()){
        val gameuistate by gameViewModel.uistate.collectAsState()
        var expandedHint by mutableStateOf(false)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameLayout(
                imageId = gameuistate.driver1.imageId,
                userGuess = gameViewModel.userGuess,
                onValueChange = {gameViewModel.userGuessing(it)},
                keyboardOp = { gameViewModel.checkUserGuess()},
                isGuessWrong = gameuistate.isGuessedWrong,
                wordCount = gameuistate.wordCount
            )

            Button(
                onClick = {gameViewModel.checkUserGuess()},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.size(300.dp,40.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.submit),
                    style = MaterialTheme.typography.headlineMedium,
                    )
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedButton(
                onClick = { gameViewModel.skipGame() },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.size(300.dp,40.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.skip),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Button(
                onClick = {gameViewModel.showHint()},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.size(300.dp,40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text(
                    text = stringResource(id = R.string.hint),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            if(gameuistate.giveHint){
                Text(
                    text = stringResource(id = gameuistate.driver1.hintId),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        GameBar(score = gameuistate.score, wordCount = gameuistate.wordCount)
    }
}

@Composable
fun GameLayout(
    imageId : Int,
    userGuess : String,
    onValueChange : (String) -> Unit,
    keyboardOp : () -> Unit,
    isGuessWrong : Boolean,
    wordCount : Int
){
    Column(
    verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )

        ) {

            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .size(100.dp, 200.dp)
            )
//            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = userGuess,
                onValueChange = onValueChange,
                keyboardActions = KeyboardActions(
                    onDone = {keyboardOp()}
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                label = {
                    if(isGuessWrong){
                        Text(text = stringResource(id = R.string.wrong_ans))
                    }
                    else{
                        Text(text = stringResource(id = R.string.enter_guess))
                    }

                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                ),
                singleLine = true,
                shape =  MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally)
            )

        }

    }

}

@Composable
fun GameBar(
    score : Int,
    wordCount : Int
){
    Row (
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .size(60.dp)
    ){
        Card (
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
//                .background(Color.DarkGray)
                .padding(5.dp)
        ){
            Text(
                text = stringResource(id = R.string.score,score),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.width(225.dp))
        Card (
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
//                .background(Color.DarkGray)
                .padding(5.dp)
        ) {
            Text(
                text = stringResource(id = R.string.word_count, wordCount),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}