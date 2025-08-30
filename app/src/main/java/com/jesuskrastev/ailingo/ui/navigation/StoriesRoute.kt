package com.jesuskrastev.ailingo.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.games.stories.HistoryGameScreen
import com.jesuskrastev.ailingo.ui.features.games.stories.StoriesState
import com.jesuskrastev.ailingo.ui.features.games.stories.StoriesViewModel
import kotlinx.serialization.Serializable

@Serializable
object StoriesGameRoute : Destination

fun NavGraphBuilder.storiesGameScreen() {
    composable<StoriesGameRoute> {
        val vm: StoriesViewModel = hiltViewModel()
        val state by vm.state.collectAsStateWithLifecycle(initialValue = StoriesState())

        HistoryGameScreen(
            state = state,
            onEvent = vm::onEvent,
        )
    }
}