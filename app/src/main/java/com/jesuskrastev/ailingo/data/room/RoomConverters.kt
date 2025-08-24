package com.jesuskrastev.ailingo.data.room

import androidx.room.TypeConverter
import java.time.LocalDate

class RoomConverters {
    @TypeConverter
    fun fromTimestamp(time: Long): LocalDate =
        LocalDate.ofEpochDay(time)

    @TypeConverter
    fun toTimestamp(date: LocalDate): Long =
        date.toEpochDay()
}