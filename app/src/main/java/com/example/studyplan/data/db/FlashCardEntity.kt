package com.example.studyplan.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.studyplan.data.FlashCard
import com.example.studyplan.domain.flashcard.CardScheduleFactory
import java.time.LocalDate

@Entity(
    tableName = "flashcards",
    foreignKeys = [
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("noteId")]
)
data class FlashCardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val noteId: Int,
    val front: String,
    val back: String,
    // Algorithm-agnostic schedule storage:
    val due: LocalDate?,        // promoted from the payload so "cards due" is a real SQL query
    val statePayload: String    // opaque per-algorithm state (see CardSchedule.serialize)
)

fun FlashCard.toFlashCardEntity(): FlashCardEntity = FlashCardEntity(
    id = id,
    noteId = noteId,
    front = front,
    back = back,
    due = schedule.nextReviewDate,
    statePayload = schedule.serialize()
)

fun FlashCardEntity.toFlashCard(scheduleFactory: CardScheduleFactory): FlashCard = FlashCard(
    id = id,
    noteId = noteId,
    front = front,
    back = back,
    schedule = scheduleFactory.fromPayload(statePayload)
)
