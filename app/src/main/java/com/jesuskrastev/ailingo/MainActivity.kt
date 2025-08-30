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
import com.jesuskrastev.ailingo.ui.features.games.match.MatchGameScreen
import com.jesuskrastev.ailingo.ui.features.games.match.MatchState
import com.jesuskrastev.ailingo.ui.features.games.match.MatchViewModel
import com.jesuskrastev.ailingo.ui.features.games.match.WordState
import com.jesuskrastev.ailingo.ui.features.games.order.OrderGameScreen
import com.jesuskrastev.ailingo.ui.features.games.order.OrderState
import com.jesuskrastev.ailingo.ui.features.games.order.OrderViewModel
import com.jesuskrastev.ailingo.ui.features.games.stories.HistoryGameScreen
import com.jesuskrastev.ailingo.ui.features.games.stories.StoriesState
import com.jesuskrastev.ailingo.ui.features.games.stories.StoriesViewModel
import com.jesuskrastev.ailingo.ui.theme.AilingoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val vm: MatchViewModel = hiltViewModel()
            val state by vm.state.collectAsStateWithLifecycle(initialValue = MatchState())
            AilingoTheme {
                Scaffold(
                    contentWindowInsets = WindowInsets.navigationBars,
                    bottomBar = {
                        NavBar()
                    },
                ) { paddingValues ->
                    MatchGameScreen(
                        modifier = Modifier.padding(paddingValues),
                        state = state,
                        onEvent = vm::onEvent
                    )
                }
            }
        }
    }
}