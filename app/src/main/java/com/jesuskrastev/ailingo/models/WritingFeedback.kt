package com.jesuskrastev.ailingo.models

import kotlinx.serialization.Serializable

@Serializable
data class WritingFeedback(
    val error: String = "",
    val suggestion: String = "",
    val correctText: String = "",
)