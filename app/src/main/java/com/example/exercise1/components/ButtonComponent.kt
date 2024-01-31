package com.example.exercise1.components

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exercise1.R
import androidx.compose.ui.platform.LocalContext
import com.example.exercise1.ui.theme.Charcoal
import com.example.exercise1.ui.theme.GhostWhite
import kotlinx.coroutines.delay
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.exercise1.InteractionSources
import com.example.exercise1.model.Game
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.junit.Rule
import java.lang.Thread.sleep
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext



@Composable
fun ButtonComponent(value : String, color: Color, audio: Int, game: Game, interactionSources: InteractionSources, mContext: Context) {
    //updateText(game = game)
    val coroutine = rememberCoroutineScope()
    var enabled by remember { mutableStateOf(true) }

    LaunchedEffect(enabled) {
        if (enabled) return@LaunchedEffect
        else delay(600L)
        enabled = true

    }

    val interactionSource = when(value) {
        "Дельта" -> interactionSources.deltaInteractionSource
        "Гамма" -> interactionSources.gammaInteractionSource
        "Альфа" -> interactionSources.alfaInteractionSource
        "Штрих" -> interactionSources.shtrihInteractionSource
        else -> interactionSources.deltaInteractionSource
    }

    val isPressed by interactionSource.collectIsPressedAsState()
    if (isPressed) {

        LaunchedEffect(Unit) {
            coroutine.launch {
                game.computerPlay(interactionSources, mContext)
            }
        }
    }


    val newColor = if (isPressed) { Charcoal } else { color }
    val id = when(value) {
        "Дельта" -> 1
        "Гамма" -> 2
        "Альфа" -> 3
        "Штрих" -> 4
        else -> -1
    }

    if (isPressed) {
        enabled = false
        game.clickButton(
            mContext = mContext,
            audio = audio,
            id = id,
            interactionSources = interactionSources
        )

    }
        Button(
            onClick = {

                enabled = false
                      },
            interactionSource = interactionSource,
            modifier = Modifier
                .heightIn(200.dp)
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = newColor),
            enabled = enabled
        ) { Text(text = value, fontSize = 35.sp, color = GhostWhite) }





}

fun playAudio(context: Context, audio: Int) {
    val mediaPlayer = MediaPlayer.create(context, audio)
    mediaPlayer.start()
}





