package com.jesuskrastev.ailingo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.games.GamesScreen
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