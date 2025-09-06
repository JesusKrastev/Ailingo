package com.jesuskrastev.ailingo.ui.features.vocabulary.flashcards

import com.jesuskrastev.ailingo.ui.features.vocabulary.DefinitionState

data class FlashcardsState(
    val definitions: List<DefinitionState> = emptyList(),
    val isLoading: Boolean = false,
    val index: Int = 0,
)