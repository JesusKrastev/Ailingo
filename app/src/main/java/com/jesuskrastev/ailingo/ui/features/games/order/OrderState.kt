package com.jesuskrastev.ailingo.ui.features.games.order

data class OrderState(
    val isLoading: Boolean = false,
    val shuffledPhrases: List<ShuffledPhraseState> = emptyList(),
)