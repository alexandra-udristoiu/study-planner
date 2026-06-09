package com.example.studyplan.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplan.data.FlashCard
import com.example.studyplan.data.FlashCardRepository
import com.example.studyplan.domain.flashcard.Rating
import kotlinx.coroutines.launch
import java.time.LocalDate

class FlashCardsViewModel(
    private val repository: FlashCardRepository
) : ViewModel() {

    // The cards due for review. Loaded once; each action below patches it
    // instead of re-querying.
    var dueCards by mutableStateOf<List<FlashCard>>(emptyList())
        private set

    // The cards belonging to the note currently open in NoteDetailScreen.
    // Reloaded per note; add/update/delete patch it in memory.
    var cardsForNote by mutableStateOf<List<FlashCard>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            dueCards = repository.getDueCards()
        }
    }

    /** Loads the cards for [noteId] into [cardsForNote] when the cards screen opens. */
    fun loadCardsForNote(noteId: Int) {
        // Drop the previous note's cards so they don't show for a frame while loading.
        cardsForNote = emptyList()
        viewModelScope.launch {
            cardsForNote = repository.getCardsForNote(noteId)
        }
    }

    fun addCard(noteId: Int, front: String, back: String) {
        viewModelScope.launch {
            // A new card has no review date yet, so it's due immediately.
            val card = repository.addCard(noteId, front, back)
            dueCards = dueCards + card
            cardsForNote = cardsForNote + card
        }
    }

    fun updateCard(card: FlashCard) {
        viewModelScope.launch {
            repository.updateCard(card)
            dueCards = dueCards.map { if (it.id == card.id) card else it }
            cardsForNote = cardsForNote.map { if (it.id == card.id) card else it }
        }
    }

    fun deleteCard(card: FlashCard) {
        viewModelScope.launch {
            repository.deleteCard(card)
            dueCards = dueCards.filterNot { it.id == card.id }
            cardsForNote = cardsForNote.filterNot { it.id == card.id }
        }
    }

    fun reviewCard(card: FlashCard, rating: Rating) {
        viewModelScope.launch {
            val due = card.schedule.review(rating)
            repository.updateCard(card)
            val stillDueToday = due == null || !due.isAfter(LocalDate.now())
            dueCards = dueCards.filterNot { it.id == card.id }
            if (stillDueToday) {
                // Re-show later this session instead of immediately.
                dueCards = dueCards + card
            }
        }
    }
}
