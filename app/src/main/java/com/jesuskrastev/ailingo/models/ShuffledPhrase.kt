package com.jesuskrastev.ailingo.models

import kotlinx.serialization.Serializable

@Serializable
data class ShuffledPhrase(
    val words: List<String> = emptyList(),
    val orderedWords: List<String> = emptyList(),
)