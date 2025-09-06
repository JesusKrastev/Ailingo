package com.jesuskrastev.ailingo.ui.features.vocabulary

import com.jesuskrastev.ailingo.models.Definition

data class DefinitionState(
    val id: String = "",
    val text: String = "",
    val translation: String = "",
    val isLearned: Boolean = false,
)

fun Definition.toDefinitionState(): DefinitionState =
    DefinitionState(
        id = id,
        text = text,
        translation = translation,
        isLearned = isLearned
    )

fun DefinitionState.toDefinition(): Definition =
    Definition(
        id = id,
        text = text,
        translation = translation,
        isLearned = isLearned,
    )