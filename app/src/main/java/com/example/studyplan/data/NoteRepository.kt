package com.example.studyplan.data

import com.example.studyplan.data.db.NoteDao
import com.example.studyplan.data.db.toStudyNote

class NoteRepository(private val dao: NoteDao) {

    suspend fun getNotes(): List<StudyNote> =
        dao.getAllNotes().map { it.toStudyNote() }

    suspend fun addNote(title: String, topicName: String, content: String, summary: String) {
        dao.insertNote(StudyNote(0, title, topicName, content, summary).toNoteEntity())
    }

    suspend fun deleteNote(note: StudyNote) {
        dao.deleteNote(note.toNoteEntity())
    }

    suspend fun updateNote(note: StudyNote) {
        dao.updateNote(note.toNoteEntity())
    }

    suspend fun filterByTopic(topicName: String): List<StudyNote> =
        dao.getNotesByTopic(topicName).map { it.toStudyNote() }

    suspend fun getNoteById(id: Int): StudyNote? =
        dao.getNoteById(id)?.toStudyNote()
}
