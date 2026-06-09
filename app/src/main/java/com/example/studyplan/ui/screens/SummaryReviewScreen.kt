package com.example.studyplan.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyplan.ui.theme.StudyPlanTheme

/**
 * Shows a freshly generated summary for the user to confirm before it is saved
 * onto the note.
 *
 * This screen is intentionally "dumb": it only renders the [summary] text and
 * forwards the user's choice via [onAccept] / [onCancel]. Generating, saving,
 * and navigation are the caller's responsibility.
 *
 * @param summary the generated text to review.
 * @param isLoading true while the summary is still being generated; shows a spinner.
 * @param errorMessage non-null when generation failed; shows the message and a retry button.
 * @param onAccept invoked when the user accepts the summary (caller should save it).
 * @param onCancel invoked when the user discards the summary or navigates back.
 * @param onRetry invoked when the user retries after an error.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryReviewScreen(
    summary: String,
    isLoading: Boolean,
    errorMessage: String?,
    onAccept: () -> Unit,
    onCancel: () -> Unit,
    onRetry: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Review Summary") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Cancel")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }
        if (errorMessage != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Retry")
                }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel")
                }
            }
            return@Scaffold
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Generated summary",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = summary.ifBlank { "No summary was generated." },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(Modifier.height(4.dp))
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Discard")
            }
            Button(
                onClick = onAccept,
                enabled = summary.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Accept")
            }
        }
    }
}

@Preview(showBackground = true, name = "Loaded")
@Composable
private fun SummaryReviewScreenPreview() {
    StudyPlanTheme {
        SummaryReviewScreen(
            summary = "A neural network detects patterns in data, loosely modelled on the human brain.",
            isLoading = false,
            errorMessage = null,
            onAccept = {},
            onCancel = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading")
@Composable
private fun SummaryReviewScreenLoadingPreview() {
    StudyPlanTheme {
        SummaryReviewScreen(
            summary = "",
            isLoading = true,
            errorMessage = null,
            onAccept = {},
            onCancel = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, name = "Error")
@Composable
private fun SummaryReviewScreenErrorPreview() {
    StudyPlanTheme {
        SummaryReviewScreen(
            summary = "",
            isLoading = false,
            errorMessage = "Couldn't reach the summary service. Check that the backend is running and try again.",
            onAccept = {},
            onCancel = {},
            onRetry = {}
        )
    }
}
