package com.jesuskrastev.ailingo.ui.features.home

import com.jesuskrastev.ailingo.models.Term
import java.time.LocalDate

data class TermState(
    val term: String = "",
    val translation: String = "",
    val definition: String = "",
)

fun Term.toTermState(): TermState =
    TermState(
        term = term,
        translation = translation,
        definition = definition,
    )