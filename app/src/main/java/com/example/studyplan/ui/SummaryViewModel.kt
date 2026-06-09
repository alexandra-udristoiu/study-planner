package com.example.studyplan.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplan.data.StudyNote
import com.example.studyplan.domain.summary.SummaryGenerationException
import com.example.studyplan.domain.summary.SummaryGenerator
import kotlinx.coroutines.launch

/**
 * Owns AI-summary generation only. Saving an accepted summary onto a note is a
 * note mutation, so that stays in [NoteViewModel]; the screen wires the two together.
 */
class SummaryViewModel(
    private val summaryGenerator: SummaryGenerator,
) : ViewModel() {

    var generatedSummary by mutableStateOf<String?>(null)
        private set

    var isGenerating by mutableStateOf(false)
        private set

    // Non-null when the last generation attempt failed; holds a message to show.
    var error by mutableStateOf<String?>(null)
        private set

    fun generate(note: StudyNote) {
        viewModelScope.launch {
            isGenerating = true
            error = null
            generatedSummary = null
            try {
                generatedSummary = summaryGenerator.generateSummary(note)
            } catch (e: SummaryGenerationException) {
                error = e.message ?: "Failed to generate a summary."
            } finally {
                isGenerating = false
            }
        }
    }

    fun clear() {
        generatedSummary = null
        error = null
    }
}
