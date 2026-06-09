package com.example.studyplan.domain.flashcard

import com.example.studyplan.data.FlashCard
import com.example.studyplan.data.StudyNote

interface FlashCardsGenerator {

    fun generateFlashCards(note: StudyNote): List<FlashCard>
}