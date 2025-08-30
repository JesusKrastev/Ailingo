package com.jesuskrastev.ailingo.ui.features.games.phrases

import com.jesuskrastev.ailingo.models.Phrase
import kotlinx.serialization.Serializable

data class PhraseState(
    val prefix: String = "",
    val suffix: String = "",
    val missingWord: String = "",
    val options: List<String> = emptyList(),
    val selectedOption: String = "",
    val isCorrect: Boolean = false,
    val isAnswered: Boolean = false,
)

fun Phrase.toPhraseState(): PhraseState =
    PhraseState(
        prefix = prefix,
        suffix = suffix,
        missingWord = missingWord,
        options = options,
    )