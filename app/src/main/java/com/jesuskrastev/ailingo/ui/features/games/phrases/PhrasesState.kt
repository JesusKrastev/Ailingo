package com.jesuskrastev.ailingo.ui.features.games.phrases

data class PhrasesState(
    val isLoading: Boolean = false,
    val phrases: List<PhraseState> = emptyList(),
)