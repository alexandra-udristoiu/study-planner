package com.example.studyplan.data.db

import androidx.room.TypeConverter
import java.time.LocalDate

/** Tells Room how to store types it doesn't support natively. */
class Converters {

    @TypeConverter
    fun fromEpochDay(epochDay: Long?): LocalDate? =
        epochDay?.let { LocalDate.ofEpochDay(it) }

    @TypeConverter
    fun toEpochDay(date: LocalDate?): Long? =
        date?.toEpochDay()
}
