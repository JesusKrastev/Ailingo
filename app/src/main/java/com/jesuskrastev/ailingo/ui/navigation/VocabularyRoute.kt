package com.jesuskrastev.ailingo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.vocabulary.VocabularyScreen
import kotlinx.serialization.Serializable

@Serializable
object VocabularyRoute : Destination

fun NavGraphBuilder.vocabularyScreen(
    onNavigateTo: (Destination) -> Unit,
) {
    composable<VocabularyRoute> {
        VocabularyScreen()
    }
}