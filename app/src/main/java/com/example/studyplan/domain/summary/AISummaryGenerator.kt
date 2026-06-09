package com.example.studyplan.domain.summary

import com.example.studyplan.data.StudyNote
import com.example.studyplan.data.remote.SummaryApi
import com.example.studyplan.data.remote.SummaryRequestDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * A [SummaryGenerator] that asks the StudyPlanner backend (POST /api/summarize)
 * for a real, AI-generated summary.
 *
 * @param summaryApi the Retrofit service; inject [BackendApi.summaryApi] in production.
 */
class AISummaryGenerator(
    private val summaryApi: SummaryApi,
) : SummaryGenerator {

    override suspend fun generateSummary(note: StudyNote): String {
        val text = note.content.trim()
        if (text.isEmpty()) return ""

        return withContext(Dispatchers.IO) {
            try {
                summaryApi.summarize(SummaryRequestDto(text)).summary.trim()
            } catch (e: IOException) {
                // No connection, DNS failure, or timeout — the request never
                // completed. Most likely the backend isn't running/reachable.
                throw SummaryGenerationException(
                    "Couldn't reach the summary service. Check that the backend is running and try again.",
                    e,
                )
            } catch (e: HttpException) {
                // The backend responded, but with a 4xx/5xx status.
                throw SummaryGenerationException(
                    "The summary service returned an error (${e.code()}). Please try again.",
                    e,
                )
            }
        }
    }
}
