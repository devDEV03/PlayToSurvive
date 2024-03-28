package com.example.f1game

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.K
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1game.data.listOfguide
import com.example.f1game.model.GameViewModel
import com.example.f1game.ui.theme.LightBlack
import com.example.f1game.ui.theme.guidelineBar

@SuppressLint("UnrememberedMutableState")
@Composable
fun GameScreen(gameViewModel : GameViewModel = viewModel()){
        val gameuistate by gameViewModel.uistate.collectAsState()
    val guidelinestate by mutableStateOf(false)
    Scaffold(
        topBar = { topBar1(gameuistate.guidelineShow,{gameViewModel.guidelineWindowChange()}) }
    ) {
        Spacer(Modifier.height(150.dp))
        Column {

                if(gameuistate.guidelineShow){
                    LazyRow(contentPadding = it){
                        items(listOfguide){
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(MaterialTheme.shapes.medium)
                            ) {

                                Text(text = it.guide,
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.displaySmall
                                )
                            }
                        }
                    }
                }
            }
        }


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
                    containerColor = Color.Green,
                    contentColor = Color.White
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
                    text = stringResource(R.string.for_hint, stringResource(id = gameuistate.driver1.hintId)),
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
    if(gameuistate.isGameOver){
        val activity = LocalContext.current as Activity
        AlertDialog(
            onDismissRequest = {gameViewModel.resetGame() },
            confirmButton = {
                            TextButton(onClick = {gameViewModel.resetGame() }) {
                                    Text(text = stringResource(id = R.string.play_again))
                            }
            },
            dismissButton = {
                            TextButton(onClick = { activity.finish() }) {
                                    Text(text = stringResource(id = R.string.end))
                            }
            },
            title = { Text(text = stringResource(id = R.string.congratulations))},
            text = { Text(text = stringResource(id = R.string.alertMessage,gameuistate.score))},
        )
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
        Spacer(modifier = Modifier.width(210.dp))
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

@Composable
fun IconButton1(
    expanded : Boolean,
    onClick : () -> Unit
){
    androidx.compose.material3.IconButton(onClick = onClick) {
        Icon(
            imageVector = if (!expanded) {
                Icons.Default.KeyboardArrowDown
            }
            else{
                Icons.Default.KeyboardArrowUp
                },
            contentDescription = null,
            tint = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBar1(
    expanded : Boolean,
    onClick: () -> Unit
){
    Column {
        CenterAlignedTopAppBar(
            title = {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.f1logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "PLAY TO SURVIVE",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Black,
                titleContentColor = Color.Red
            )

        )
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            ) {
                Spacer(modifier = Modifier.width(140.dp))
                Text(
                    text = stringResource(id = R.string.guidelines),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = Color.White,
                )

                IconButton1(
                    expanded = expanded,
                    onClick = onClick)
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}