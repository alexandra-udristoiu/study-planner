package com.example.studyplan.data

import com.example.studyplan.data.db.NoteEntity

data class StudyNote(
    val id: Int,
    val title: String,
    val topicName: String = "",
    val content: String,
    val summary: String = ""
)

fun StudyNote.toNoteEntity() = NoteEntity(
    id = id,
    title = title,
    topicName = topicName,
    content = content,
    summary = summary
)
