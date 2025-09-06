package com.jesuskrastev.ailingo.data.room.definition

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jesuskrastev.ailingo.data.room.term.TermEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DefinitionDao {
    @Query("SELECT * FROM definitions")
    fun get(): Flow<List<DefinitionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(definition: DefinitionEntity)

    @Query("SELECT * FROM definitions WHERE isLearned = 0")
    fun getPending(): List<DefinitionEntity>

    @Update
    suspend fun update(definition: DefinitionEntity)
}