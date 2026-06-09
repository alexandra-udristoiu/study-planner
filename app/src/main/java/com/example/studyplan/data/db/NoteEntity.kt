package com.example.studyplan.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.studyplan.data.StudyNote

@Entity(tableName = "study_notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val topicName: String,
    val content: String,
    val summary: String
)

fun NoteEntity.toStudyNote(): StudyNote = StudyNote(
    id = id,
    title = title,
    topicName = topicName,
    content = content,
    summary = summary
)
