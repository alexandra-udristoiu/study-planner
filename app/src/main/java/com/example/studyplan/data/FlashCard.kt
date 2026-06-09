package com.example.studyplan.data

import com.example.studyplan.domain.flashcard.CardSchedule

data class FlashCard(
    val id: Int,
    val noteId: Int,
    val front: String,
    val back: String,
    val schedule: CardSchedule
)
