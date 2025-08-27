package com.jesuskrastev.ailingo.ui.features.games.order

import com.jesuskrastev.ailingo.models.ShuffledPhrase

data class ShuffledPhraseState(
    val words: List<String> = emptyList(),
    val orderedWords: List<String> = emptyList(),
    val selectedWords: List<String> = emptyList(),
    val isCorrect: Boolean = false,
    val isAnswered: Boolean = false,
)

fun ShuffledPhrase.toShuffledPhraseState(): ShuffledPhraseState =
    ShuffledPhraseState(
        words = words,
        orderedWords = orderedWords,
    )