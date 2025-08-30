package com.jesuskrastev.ailingo.ui.features.games.phrases

data class CompleteState(
    val isLoading: Boolean = false,
    val phrases: List<PhraseState> = emptyList(),
)