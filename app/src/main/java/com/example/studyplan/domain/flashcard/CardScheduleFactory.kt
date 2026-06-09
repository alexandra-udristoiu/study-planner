package com.example.studyplan.domain.flashcard

/**
 * Creates and restores [CardSchedule]s for the app's chosen algorithm.
 *
 * This is the single place that knows the concrete schedule type, so the rest
 * of the app (entities, mappers, repository) stays algorithm-agnostic. To switch
 * algorithms app-wide, swap the injected implementation.
 */
interface CardScheduleFactory {

    /** A schedule for a brand-new, never-reviewed card. */
    fun newSchedule(): CardSchedule

    /** Rebuilds a schedule from a payload produced by [CardSchedule.serialize]. */
    fun fromPayload(payload: String): CardSchedule
}
