package com.jesuskrastev.ailingo.ui.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.home.HomeScreen
import com.jesuskrastev.ailingo.ui.features.home.HomeState
import com.jesuskrastev.ailingo.ui.features.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute : Destination

fun NavGraphBuilder.homeScreen(
    vm: HomeViewModel,
    onNavigateTo: (Destination) -> Unit,
) {
    composable<HomeRoute> {
        val state by vm.state.collectAsStateWithLifecycle(initialValue = HomeState())

        HomeScreen(
            state = state,
            onNavigateTo = onNavigateTo,
        )
    }
}