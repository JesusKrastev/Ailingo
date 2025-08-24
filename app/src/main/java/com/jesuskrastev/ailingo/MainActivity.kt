package com.jesuskrastev.ailingo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesuskrastev.ailingo.ui.features.components.NavBar
import com.jesuskrastev.ailingo.ui.features.games.history.HistoryGameScreen
import com.jesuskrastev.ailingo.ui.features.home.HomeScreen
import com.jesuskrastev.ailingo.ui.features.home.HomeState
import com.jesuskrastev.ailingo.ui.features.home.HomeViewModel
import com.jesuskrastev.ailingo.ui.theme.AilingoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val vm: HomeViewModel = hiltViewModel()
            val state by vm.state.collectAsStateWithLifecycle(initialValue = HomeState())
            AilingoTheme {
                Scaffold(
                    contentWindowInsets = WindowInsets.navigationBars,
                    bottomBar = {
                        NavBar()
                    },
                ) { paddingValues ->
                    HomeScreen(
                        modifier = Modifier.padding(paddingValues),
                        state = state,
                    )
                }
            }
        }
    }
}