package com.example.studyplan.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Style
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyplan.data.StudyNote
import com.example.studyplan.ui.theme.StudyPlanTheme

private val previewNotes = listOf(
    StudyNote(1, "Neural Networks", "ML", "A neural network is a series of algorithms that detect underlying relationships in a set of data.", "Inspired by the human brain"),
    StudyNote(2, "Gradient Descent", "ML", "An optimization algorithm that minimizes a loss function by iteratively moving in the direction of steepest descent.", "Core training algorithm"),
    StudyNote(3, "Kotlin Coroutines", "Android", "Coroutines are Kotlin's approach to async programming. Like Java's CompletableFuture but far less verbose.", "Async made easy"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    notes: List<StudyNote>,
    topics: List<String>,
    selectedTopic: String?,
    onTopicSelected: (String?) -> Unit,
    onAddClick: () -> Unit,
    onReviewClick: () -> Unit,
    onNoteClick: (StudyNote) -> Unit,
    onDeleteClick: (noteId: Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Study Notes") },
                actions = {
                    if (topics.isNotEmpty()) {
                        TopicFilterMenu(topics, selectedTopic, onTopicSelected)
                    }
                    IconButton(onClick = onAddClick) {
                        Icon(Icons.Default.Add, contentDescription = "Add note")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onReviewClick) {
                Icon(Icons.Default.Style, contentDescription = "Review due flashcards")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = innerPadding.calculateBottomPadding() + 8.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(notes) { note ->
                NoteCard(note, onNoteClick, onDeleteClick)
            }
        }
    }
}

@Composable
private fun TopicFilterMenu(
    topics: List<String>,
    selectedTopic: String?,
    onTopicSelected: (String?) -> Unit,
) {
    // Local UI state: is the dropdown open? Survives recomposition via remember.
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            Icons.Default.FilterList,
            // Hint the active filter to screen readers.
            contentDescription = selectedTopic?.let { "Filter: $it" } ?: "Filter by topic"
        )
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        // "All" clears the filter (null topic).
        TopicMenuItem(
            label = "All",
            isSelected = selectedTopic == null,
            onClick = {
                onTopicSelected(null)
                expanded = false
            }
        )
        topics.forEach { topic ->
            TopicMenuItem(
                label = topic,
                isSelected = selectedTopic == topic,
                onClick = {
                    onTopicSelected(topic)
                    expanded = false
                }
            )
        }
    }
}

@Composable
private fun TopicMenuItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        text = { Text(label) },
        onClick = onClick,
        // Show a check next to the currently active option.
        trailingIcon = {
            if (isSelected) Icon(Icons.Default.Check, contentDescription = null)
        }
    )
}

@Composable
private fun NoteCard(
    note: StudyNote,
    onNoteClick: (StudyNote) -> Unit,
    onDeleteClick: (Int) -> Unit,
) {
    Card(
        onClick = { onNoteClick(note) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 4.dp, top = 12.dp, bottom = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (note.topicName.isNotEmpty()) TopicChip(note.topicName) else Spacer(Modifier)
                IconButton(onClick = { onDeleteClick(note.id) }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete note",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium
            )
            if (note.summary.isNotEmpty()) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = note.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun TopicChip(topic: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = topic,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteListScreenPreview() {
    StudyPlanTheme {
        NoteListScreen(
            notes = previewNotes,
            topics = listOf("ML", "Android"),
            selectedTopic = null,
            onTopicSelected = {},
            onAddClick = {},
            onReviewClick = {},
            onNoteClick = {},
            onDeleteClick = {}
        )
    }
}
