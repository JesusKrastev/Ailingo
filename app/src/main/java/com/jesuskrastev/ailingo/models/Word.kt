package com.jesuskrastev.ailingo.models

import kotlinx.serialization.Serializable

@Serializable
data class Word(
    val id: String = "",
    val text: String = "",
    val translationId: String = "",
    val isTranslation: Boolean = false,
)