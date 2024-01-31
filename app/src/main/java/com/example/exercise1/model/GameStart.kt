package com.example.exercise1.model

import android.content.Context
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.exercise1.R
import com.example.exercise1.components.ButtonComponent
import com.example.exercise1.rememberInteractionSources
import com.example.exercise1.ui.theme.DesertSand
import com.example.exercise1.ui.theme.GhostWhite
import com.example.exercise1.ui.theme.RosyBrown
import com.example.exercise1.ui.theme.Silver
import com.example.exercise1.ui.theme.TeaRose
import kotlinx.coroutines.launch

@Composable
fun GameStart(context: Context, window: Window, navController: NavHostController, mode: String) {
    val coroutine = rememberCoroutineScope()



    val interactionSources = rememberInteractionSources()
    val game = Game(context, window, navController)
    val maxLevel = game.getMaxLevel(context)
    if (mode == "FreePlay") {
        game.gameOver = true
        game.gameStarted = true

    }
    if (!game.gameStarted) {
        LaunchedEffect(Unit) {
            coroutine.launch {
                game.computerPlay(interactionSources, context)
            }
        }
    }

    game.gameStarted = true

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = GhostWhite)) {
        Row(modifier = Modifier
            .weight(0.5f)
            .fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { navController.navigate("Menu")}) { Text("Назад")}
                Text(text = "Рекорд - $maxLevel", textAlign = TextAlign.Center)
            }
        }
        Row(modifier = Modifier
            .weight(2.0f)
            .fillMaxWidth()) {
            Column(modifier = Modifier.weight(0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                ButtonComponent("Дельта", RosyBrown, R.raw.delta, game, interactionSources, context)
                ButtonComponent("Гамма", TeaRose, R.raw.gamma, game, interactionSources, context)

            }
            Column(modifier = Modifier.weight(0.5f)) {
                ButtonComponent("Альфа", DesertSand, R.raw.alfa, game, interactionSources, context)
                ButtonComponent("Штрих", Silver, R.raw.shtrih, game, interactionSources, context)

            }
        }
    }

}