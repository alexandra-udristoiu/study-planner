package com.example.studyplan.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studyplan.data.FlashCard

/** Whether the card editor dialog is closed, or open for adding / editing a card. */
internal sealed interface CardEditor {

    /** The dialog is not showing. */
    data object Closed : CardEditor

    /** The dialog is showing. Carries the title and the text the fields start from. */
    sealed interface Open : CardEditor {
        val title: String
        val front: String
        val back: String
    }

    /** Add a new card, starting from blank fields. */
    data object New : Open {
        override val title = "New flashcard"
        override val front = ""
        override val back = ""
    }

    /** Edit an existing card, starting from its current text. */
    data class Edit(val card: FlashCard) : Open {
        override val title = "Edit flashcard"
        override val front get() = card.front
        override val back get() = card.back
    }
}

/**
 * Add/edit dialog with a front and a back field. Starts from the text in [editor],
 * holds the in-progress edits in its own state, and reports the final values back
 * through [onSave]; Save is disabled until both fields are filled.
 *
 * @param editor the open state (New or Edit) that supplies the title and initial text.
 * @param onSave invoked with the trimmed front/back text when the user confirms.
 */
@Composable
internal fun CardEditorDialog(
    editor: CardEditor.Open,
    onDismiss: () -> Unit,
    onSave: (front: String, back: String) -> Unit,
) {
    var front by remember { mutableStateOf(editor.front) }
    var back by remember { mutableStateOf(editor.back) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(editor.title) },
        text = {
            Column {
                OutlinedTextField(
                    value = front,
                    onValueChange = { front = it },
                    label = { Text("Front") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = back,
                    onValueChange = { back = it },
                    label = { Text("Back") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(front.trim(), back.trim()) },
                enabled = front.isNotBlank() && back.isNotBlank(),
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
    )
}
