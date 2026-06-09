package com.example.studyplan.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * v1 -> v2: adds the `flashcards` table. Existing notes are untouched.
 *
 * The SQL must produce a table that matches what Room generates for
 * [FlashCardEntity] (see StudyPlanDatabase_Impl.createAllTables after a build),
 * otherwise Room's schema validation fails on first open.
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `flashcards` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`noteId` INTEGER NOT NULL, " +
                "`front` TEXT NOT NULL, " +
                "`back` TEXT NOT NULL, " +
                "`due` INTEGER, " +
                "`statePayload` TEXT NOT NULL, " +
                "FOREIGN KEY(`noteId`) REFERENCES `study_notes`(`id`) " +
                "ON UPDATE NO ACTION ON DELETE CASCADE )"
        )
        db.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_flashcards_noteId` " +
                "ON `flashcards` (`noteId`)"
        )
    }
}
