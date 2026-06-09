package com.example.studyplan.data

import com.example.studyplan.data.db.FlashCardDao
import com.example.studyplan.data.db.toFlashCard
import com.example.studyplan.data.db.toFlashCardEntity
import com.example.studyplan.domain.flashcard.CardScheduleFactory
import java.time.LocalDate

class FlashCardRepository(
    private val dao: FlashCardDao,
    private val scheduleFactory: CardScheduleFactory
) {

    suspend fun getCardsForNote(noteId: Int): List<FlashCard> =
        dao.getFlashCardsForNote(noteId).map { it.toFlashCard(scheduleFactory) }

    suspend fun getDueCards(today: LocalDate = LocalDate.now()): List<FlashCard> =
        dao.getDueFlashCards(today).map { it.toFlashCard(scheduleFactory) }

    /** Inserts a new card and returns it with its generated id. */
    suspend fun addCard(noteId: Int, front: String, back: String): FlashCard {
        val card = FlashCard(0, noteId, front, back, scheduleFactory.newSchedule())
        val id = dao.insertFlashCard(card.toFlashCardEntity()).toInt()
        return card.copy(id = id)
    }

    suspend fun updateCard(card: FlashCard) {
        dao.updateFlashCard(card.toFlashCardEntity())
    }

    suspend fun deleteCard(card: FlashCard) {
        dao.deleteFlashCard(card.toFlashCardEntity())
    }
}
