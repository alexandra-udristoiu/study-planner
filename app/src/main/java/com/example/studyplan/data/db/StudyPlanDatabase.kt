package com.example.studyplan.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [NoteEntity::class, FlashCardEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StudyPlanDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun flashCardDao(): FlashCardDao

    companion object {
        @Volatile
        private var INSTANCE: StudyPlanDatabase? = null

        fun getInstance(context: Context): StudyPlanDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    StudyPlanDatabase::class.java,
                    "study_notes_db"
                ).addMigrations(MIGRATION_1_2).build().also { INSTANCE = it }
            }
    }
}
