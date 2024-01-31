package com.example.exercise1.model

import android.content.Context
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Nav(context: Context, window: Window) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Menu" ) {
        composable(route = "Menu") {
            Menu(navController)
        }

        composable(route = "AboutScreen") {
            AboutScreen(context, navController)
        }

        composable("GameStart/{gameMode}") { backStackEntry ->
            val gameMode = when (val mode = backStackEntry.arguments?.getString("gameMode")) {
                GameMode.NORMAL.toString() -> GameMode.NORMAL
                GameMode.FREE_PLAY.toString() -> GameMode.FREE_PLAY
                else -> GameMode.NORMAL
            }

            when (gameMode) {
                GameMode.NORMAL -> {
                    GameStart(context = context, window = window, navController = navController, "Normal")
                }
                GameMode.FREE_PLAY -> {
                    GameStart(context = context, window = window, navController = navController, "FreePlay")
                }
            }
        }
    }
}


