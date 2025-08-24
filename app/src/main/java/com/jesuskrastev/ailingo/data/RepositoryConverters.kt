package com.jesuskrastev.ailingo.data

import com.jesuskrastev.ailingo.data.room.term.TermEntity
import com.jesuskrastev.ailingo.models.Term
import java.time.LocalDate

fun TermEntity.toTerm(): Term =
    Term(
        term = term,
        definition = definition,
        translation = translation,
    )

fun Term.toTermEntity(): TermEntity =
    TermEntity(
        term = term,
        definition = definition,
        translation = translation,
        createdAt = LocalDate.now(),
    )