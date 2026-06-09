package com.example.studyplan.domain.summary

import com.example.studyplan.data.StudyNote

interface SummaryGenerator {

    suspend fun generateSummary(note: StudyNote): String
}