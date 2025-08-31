package com.jesuskrastev.ailingo.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.games.GamesScreen
import com.jesuskrastev.ailingo.ui.features.games.match.MatchGameScreen
import com.jesuskrastev.ailingo.ui.features.games.match.MatchState
import com.jesuskrastev.ailingo.ui.features.games.match.MatchViewModel
import com.jesuskrastev.ailingo.ui.features.games.order.OrderGameScreen
import com.jesuskrastev.ailingo.ui.features.games.order.OrderState
import com.jesuskrastev.ailingo.ui.features.games.order.OrderViewModel
import com.jesuskrastev.ailingo.ui.features.games.stories.HistoryGameScreen
import com.jesuskrastev.ailingo.ui.features.games.stories.StoriesState
import com.jesuskrastev.ailingo.ui.features.games.stories.StoriesViewModel
import kotlinx.serialization.Serializable

@Serializable
object GamesRoute : Destination

fun NavGraphBuilder.gamesScreen(
    onNavigateTo: (Destination) -> Unit,
) {
    composable<GamesRoute> {
        GamesScreen(
            onNavigateTo = onNavigateTo
        )
    }
}

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

@Serializable
object OrderGameRoute : Destination

fun NavGraphBuilder.orderGameScreen() {
    composable<OrderGameRoute> {
        val vm: OrderViewModel = hiltViewModel()
        val state by vm.state.collectAsStateWithLifecycle(initialValue = OrderState())

        OrderGameScreen(
            state = state,
            onEvent = vm::onEvent,
        )
    }
}

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