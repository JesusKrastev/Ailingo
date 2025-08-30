package com.jesuskrastev.ailingo.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.games.match.MatchGameScreen
import com.jesuskrastev.ailingo.ui.features.games.match.MatchState
import com.jesuskrastev.ailingo.ui.features.games.match.MatchViewModel
import kotlinx.serialization.Serializable

@Serializable
object MatchGameRoute : Destination

fun NavGraphBuilder.matchGameScreen() {
    composable<MatchGameRoute> {
        val vm: MatchViewModel = hiltViewModel()
        val state by vm.state.collectAsStateWithLifecycle(initialValue = MatchState())

        MatchGameScreen(
            state = state,
            onEvent = vm::onEvent,
        )
    }
}