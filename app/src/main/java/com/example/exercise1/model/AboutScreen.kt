package com.example.exercise1.model

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun AboutScreen(context : Context, navController: NavHostController) {
    val game = Game(context, null, navController)
    Column() {
        Text(text = "Рекорд --- ${game.getMaxLevel(context)}")
        Text(text = "Игра проигрывает определенную последовательность звуков " +
                "Затем, нужно повторить последовательность. После этого начинается следующий уровень." +
                "С каждым новом уровнем количество звуков увеличивается.")
        Text(text = "Автор - brittnyc (@magekknight)")
        Button(onClick = { navController.navigate("Menu")}) { Text("Назад")}
    }
}