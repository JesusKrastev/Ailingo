package com.jesuskrastev.ailingo.data.room.term

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TermDao {
    @Query("SELECT * FROM terms LIMIT 1")
    fun get(): List<TermEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(term: TermEntity)

    @Query("DELETE FROM terms")
    suspend fun deleteAll()
}