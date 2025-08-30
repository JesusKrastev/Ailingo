package com.jesuskrastev.ailingo.ui.features.games.match

import com.jesuskrastev.ailingo.models.Word

data class WordState(
    val id: String = "",
    val text: String = "",
    val translationId: String = "",
    val selectedId: String = "",
    val isMatched: Boolean = false,
    val isSelected: Boolean = false,
    val isTranslation: Boolean = false,
    val isCorrect: Boolean = false,
    val isAnswered: Boolean = false
)

fun Word.toWordState(): WordState =
    WordState(
        id = id,
        text = text,
        translationId = translationId,
        isTranslation = isTranslation,
    )