package com.example.studyplan.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDate

@Dao
interface FlashCardDao {

    @Insert
    suspend fun insertFlashCard(card: FlashCardEntity): Long   // returns the generated id
    @Update
    suspend fun updateFlashCard(card: FlashCardEntity)
    @Delete
    suspend fun deleteFlashCard(card: FlashCardEntity)

    @Query("SELECT * FROM flashcards WHERE noteId = :noteId ORDER BY id")
    suspend fun getFlashCardsForNote(noteId: Int): List<FlashCardEntity>

    // New cards (due IS NULL) are due immediately; reviewed cards are due when due <= today.
    @Query("SELECT * FROM flashcards WHERE due IS NULL OR due <= :today ORDER BY id")
    suspend fun getDueFlashCards(today: LocalDate): List<FlashCardEntity>
}
