package com.jesuskrastev.ailingo.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.games.phrases.CompleteGameScreen
import com.jesuskrastev.ailingo.ui.features.games.phrases.CompleteState
import com.jesuskrastev.ailingo.ui.features.games.phrases.PhrasesVieModel
import kotlinx.serialization.Serializable

@Serializable
object CompleteGameRoute : Destination

fun NavGraphBuilder.completeGameScreen() {
    composable<CompleteGameRoute> {
        val vm: PhrasesVieModel = hiltViewModel()
        val state by vm.state.collectAsStateWithLifecycle(initialValue = CompleteState())

        CompleteGameScreen(
            state = state,
            onEvent = vm::onEvent,
        )
    }
}