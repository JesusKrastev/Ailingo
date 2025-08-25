package com.jesuskrastev.ailingo.ui.features.games.phrases

import kotlinx.serialization.Serializable

@Serializable
data class PhraseState(
    val prefix: String = "",
    val suffix: String = "",
    val missingWord: String = "",
    val options: List<String> = emptyList(),
    val selectedOption: String = "",
    val isCorrect: Boolean = false,
    val isAnswered: Boolean = false,
)