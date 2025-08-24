package com.jesuskrastev.ailingo.models

import kotlinx.serialization.Serializable

@Serializable
data class Term(
    val term: String,
    val translation: String,
    val definition: String,
)