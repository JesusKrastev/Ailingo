package com.jesuskrastev.ailingo.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.games.order.OrderGameScreen
import com.jesuskrastev.ailingo.ui.features.games.order.OrderState
import com.jesuskrastev.ailingo.ui.features.games.order.OrderViewModel
import kotlinx.serialization.Serializable

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