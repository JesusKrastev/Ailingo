package com.jesuskrastev.ailingo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.jesuskrastev.ailingo.ui.features.games.GamesScreen
import com.jesuskrastev.ailingo.ui.features.home.HomeScreen
import com.jesuskrastev.ailingo.ui.features.vocabulary.VocabularyScreen
import com.jesuskrastev.ailingo.ui.theme.AilingoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AilingoTheme {
                Scaffold(
                    contentWindowInsets = WindowInsets.navigationBars,
                ) { paddingValues ->
                    VocabularyScreen(
                        modifier = Modifier.padding(paddingValues),
                    )
                }
            }
        }
    }
}