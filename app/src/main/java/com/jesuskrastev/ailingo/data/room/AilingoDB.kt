package com.jesuskrastev.ailingo.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jesuskrastev.ailingo.data.room.term.TermDao
import com.jesuskrastev.ailingo.data.room.term.TermEntity

@Database(
    entities = [TermEntity::class],
    version = 1,
)
@TypeConverters(RoomConverters::class)
abstract class AilingoDB: RoomDatabase() {
    abstract fun termDao(): TermDao

    companion object {
        fun getDatabase(
            context: Context
        ) = Room.databaseBuilder(
            context = context,
            AilingoDB::class.java,
            "ailingo"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}