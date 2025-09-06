package com.jesuskrastev.ailingo.data.room.definition

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "definitions")
data class DefinitionEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val definition: String,
    val translation: String,
    val isLearned: Boolean,
)