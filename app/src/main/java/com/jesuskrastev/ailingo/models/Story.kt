package com.jesuskrastev.ailingo.models

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val title: String = "",
    val text: String = "",
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctOption: String = "",
)
