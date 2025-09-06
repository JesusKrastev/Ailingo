package com.jesuskrastev.ailingo.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.ailingo.ui.features.vocabulary.VocabularyScreen
import com.jesuskrastev.ailingo.ui.features.vocabulary.VocabularyState
import com.jesuskrastev.ailingo.ui.features.vocabulary.VocabularyViewModel
import com.jesuskrastev.ailingo.ui.features.vocabulary.flashcards.FlashcardsScreen
import com.jesuskrastev.ailingo.ui.features.vocabulary.flashcards.FlashcardsState
import com.jesuskrastev.ailingo.ui.features.vocabulary.flashcards.FlashcardsViewModel
import kotlinx.serialization.Serializable

@Serializable
object VocabularyRoute : Destination

fun NavGraphBuilder.vocabularyScreen(
    vm: VocabularyViewModel,
    onNavigateTo: (Destination) -> Unit,
) {
    composable<VocabularyRoute> {
        val state by vm.state.collectAsStateWithLifecycle(initialValue = VocabularyState())

        VocabularyScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateTo = onNavigateTo,
        )
    }
}

@Serializable
object FlashcardsRoute : Destination

fun NavGraphBuilder.flashcardsScreen() {
    composable<FlashcardsRoute> {
        val vm: FlashcardsViewModel = hiltViewModel()
        val state by vm.state.collectAsStateWithLifecycle(initialValue = FlashcardsState())

        FlashcardsScreen(
            state = state,
            onEvent = vm::onEvent
        )
    }
}