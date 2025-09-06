package com.jesuskrastev.ailingo.data

import com.jesuskrastev.ailingo.data.room.definition.DefinitionEntity
import com.jesuskrastev.ailingo.data.room.term.TermEntity
import com.jesuskrastev.ailingo.models.Definition
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

fun DefinitionEntity.toDefinition(): Definition =
    Definition(
        id = id,
        text = definition,
        translation = translation,
        isLearned = isLearned,
    )

fun Definition.toDefinitionEntity(): DefinitionEntity =
    DefinitionEntity(
        id = id,
        definition = text,
        translation = translation,
        isLearned = isLearned,
    )