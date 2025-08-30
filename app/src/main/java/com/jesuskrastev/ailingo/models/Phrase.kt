package com.jesuskrastev.ailingo.models

import kotlinx.serialization.Serializable

@Serializable
data class Phrase(
    val prefix: String = "",
    val suffix: String = "",
    val missingWord: String = "",
    val options: List<String> = emptyList(),
)