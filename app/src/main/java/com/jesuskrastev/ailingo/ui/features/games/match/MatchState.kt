package com.jesuskrastev.ailingo.ui.features.games.match

data class MatchState(
    val words: List<List<WordState>> = emptyList(),
    val isLoading: Boolean = false,
)