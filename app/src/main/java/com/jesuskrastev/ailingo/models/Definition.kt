package com.jesuskrastev.ailingo.models

import kotlinx.serialization.Serializable

@Serializable
data class Definition(
    val id: String = "",
    val text: String = "",
    val translation: String = "",
    val isLearned: Boolean = false,
)