package com.example.exercise1.model


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

sealed class GameMode {
    object NORMAL : GameMode()
    object FREE_PLAY : GameMode()
}

@Composable
fun Menu(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = {navController.navigate("GameStart/${GameMode.NORMAL}")}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Начать игру")
        }

        Button(onClick = {navController.navigate("GameStart/${GameMode.FREE_PLAY}")}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Free play")
        }

        Button(onClick = {navController.navigate("AboutScreen")}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Об игре")
        }

    }

}