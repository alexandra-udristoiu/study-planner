package com.example.studyplan.domain.flashcard

/**
 * How well the user recalled a card. Algorithm-agnostic input: each [CardSchedule]
 * implementation interprets [quality] in its own way.
 *
 * For SM-2, any quality < 3 counts as a failed recall.
 */
enum class Rating(val quality: Int) {
    AGAIN(1),
    HARD(3),
    GOOD(4),
    EASY(5),
}
