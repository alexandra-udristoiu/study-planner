package com.example.studyplan.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplan.data.NoteRepository
import com.example.studyplan.data.StudyNote
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository,
) : ViewModel() {

    // The full, unfiltered list loaded from the repository.
    private var allNotes by mutableStateOf<List<StudyNote>>(emptyList())

    // null = "All". When set, only notes with this topic are shown.
    var selectedTopic by mutableStateOf<String?>(null)
        private set

    // The list the screen actually renders: all notes, or just the chosen topic.
    val notes: List<StudyNote>
        get() = selectedTopic?.let { topic -> allNotes.filter { it.topicName == topic } }
            ?: allNotes

    // Distinct, non-empty topics for building the filter chips.
    val topics: List<String>
        get() = allNotes.map { it.topicName }.filter { it.isNotEmpty() }.distinct().sorted()

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            allNotes = repository.getNotes()
        }
    }

    fun selectTopic(topic: String?) {
        selectedTopic = topic
    }

    fun addNote(title: String, topicName: String, content: String, summary: String) {
        viewModelScope.launch {
            repository.addNote(title, topicName, content, summary)
            allNotes = repository.getNotes()
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            allNotes.find { it.id == noteId }?.let { repository.deleteNote(it) }
            allNotes = repository.getNotes()
        }
    }

    fun updateNote(note: StudyNote) {
        viewModelScope.launch {
            repository.updateNote(note)
            allNotes = repository.getNotes()
        }
    }

    fun findById(noteId: Int): StudyNote? = allNotes.find { it.id == noteId }
}
