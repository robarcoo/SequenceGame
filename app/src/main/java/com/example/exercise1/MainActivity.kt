package com.example.exercise1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.exercise1.model.Nav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Nav(this, window)

                }
        }
    }
}




data class InteractionSources(
    val deltaInteractionSource: MutableInteractionSource,
    val gammaInteractionSource: MutableInteractionSource,
    val alfaInteractionSource: MutableInteractionSource,
    val shtrihInteractionSource: MutableInteractionSource
)

@Composable
fun rememberInteractionSources(): InteractionSources {
    return remember {
        InteractionSources(
            deltaInteractionSource = MutableInteractionSource(),
            gammaInteractionSource = MutableInteractionSource(),
            alfaInteractionSource = MutableInteractionSource(),
            shtrihInteractionSource = MutableInteractionSource()
        )
    }
}


