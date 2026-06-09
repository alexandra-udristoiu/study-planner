package com.example.studyplan.domain.flashcard

import java.time.LocalDate

interface CardSchedule {

    /** The date this card is next due, or null if it has never been reviewed. */
    val nextReviewDate: LocalDate?

    fun review(rating: Rating, today: LocalDate = LocalDate.now()): LocalDate?

    /**
     * Serializes this schedule's internal state to an opaque string for storage.
     * Rebuilt by the matching [CardScheduleFactory.fromPayload].
     */
    fun serialize(): String
}
