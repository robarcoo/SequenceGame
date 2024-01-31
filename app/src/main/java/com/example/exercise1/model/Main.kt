package com.example.exercise1.model

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.navigation.NavController
import com.example.exercise1.InteractionSources
import com.example.exercise1.R
import com.example.exercise1.components.playAudio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class Game(context: Context, window: Window?, navController: NavController) : View(context) {
    var sequence by mutableStateOf(mutableListOf<Int>(1, 2, 3, 4))
    val playerInput = mutableListOf<Int>()
    val gameWindow = window
    var gameOver by mutableStateOf(false)
    var playerTurn by mutableStateOf(false)
    var count by mutableStateOf(0)
    var level by mutableStateOf(1)
    var maxLevel = getMaxLevel(context)
    var gameStarted = false
    val navController = navController

    fun saveMaxLevel(context: Context, maxLevel: Int) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("MaxLevel", maxLevel)
        editor.apply()
    }

    fun getMaxLevel(context: Context): Int {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("MaxLevel", 1)
    }

    fun nextLevel() {
        count = 0
        level++
        if (level > getMaxLevel(context)) { saveMaxLevel(context, level) }
        sequence.add((1..4).random())
        Toast.makeText(context, "Уровень $level", Toast.LENGTH_SHORT).show()
        playerInput.clear()
        playerTurn = false
    }

    @Composable
    fun gameOver(interactionSources: InteractionSources) {
        val scope = CoroutineScope(Dispatchers.Main)
        if (level > getMaxLevel(context)) { saveMaxLevel(context, level) }
        sequence = mutableListOf<Int>(1, 2, 3, 4)
        gameOver = false
        val alertDialog = AlertDialog.Builder(this.context)
            .setTitle("Конец игры")
            .setMessage("Уровень - $level \nРекорд - ${getMaxLevel(context)}")
            .setPositiveButton("Новая игра") { dialog, _ ->
                // Code to execute when OK button is clicked

                playerInput.clear()
                scope.launch {
                    withContext(Dispatchers.Main) {
                        computerPlay(interactionSources, context)
                    }
                }
                dialog.dismiss()

            }
            .setNegativeButton("В меню") { dialog, _ ->
                navController.navigate("Menu")
                dialog.dismiss()
            }
            .create()

        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                playerInput.clear()
                alertDialog.dismiss()
                scope.launch {
                    computerPlay(interactionSources, context)
                }

            }
        }

        alertDialog.show()

        level = 1
        count = 0


    }

    suspend fun computerPlay(interactionSources: InteractionSources, context: Context)  {
        gameStarted = true
        if (playerInput.size == 0) {
    gameWindow?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
            TimeUnit.SECONDS.sleep(1L)
    for (i in 0 until sequence.size) {
        delay(100L)
        pressButton(sequence[i], interactionSources, context)
        delay(100L)
        TimeUnit.SECONDS.sleep(1L)
    }
            playerTurn = true
        }
        gameWindow?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    @Composable
    fun playerPlay(interactionSources: InteractionSources) {
        if (!gameOver && (playerInput.size - 1) == count) {
                if (playerInput[count] == sequence[count]) {
                    count += 1
                } else {
                    gameOver = true
                    gameOver(interactionSources)

                }
            }
        if (playerInput.size == sequence.size && !gameOver) {
            nextLevel()
        }
    }

    @Composable
    fun clickButton(mContext: Context, audio: Int, id : Int, interactionSources: InteractionSources)  {

        //game.playerTurn = true
        playAudio(mContext, audio)
        playerInput.add(id)
        playerPlay(interactionSources)
        //Toast.makeText(mContext, "new level: ${game.sequence}", Toast.LENGTH_SHORT).show()
    }

    suspend fun pressButton(choice: Int, interactionSources: InteractionSources, context : Context) {

        val press = PressInteraction.Press(Offset.Zero)
        //delay(250)
        var audio : Int = 0
        when (choice) {
            1 -> {
                audio = R.raw.delta
                interactionSources.deltaInteractionSource.emit(press)
                playAudio(context, audio)
                interactionSources.deltaInteractionSource.emit(PressInteraction.Release(press))
            }
            2 -> {
                audio = R.raw.gamma
                interactionSources.gammaInteractionSource.emit(press)
                playAudio(context, audio)
                interactionSources.gammaInteractionSource.emit(PressInteraction.Release(press))
            }
            3 -> {
                audio = R.raw.alfa
                interactionSources.alfaInteractionSource.emit(press)
                playAudio(context, audio)
                interactionSources.alfaInteractionSource.emit(PressInteraction.Release(press))
            }
            4 -> {
                audio = R.raw.shtrih
                interactionSources.shtrihInteractionSource.emit(press)
                playAudio(context, audio)
                interactionSources.shtrihInteractionSource.emit(PressInteraction.Release(press))
            }
        }


    }
}
