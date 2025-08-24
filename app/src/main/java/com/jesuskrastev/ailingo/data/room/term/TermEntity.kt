package com.jesuskrastev.ailingo.data.room.term

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID

@Entity(tableName = "terms")
data class TermEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val term: String,
    val translation: String,
    val definition: String,
    val createdAt: LocalDate,
)