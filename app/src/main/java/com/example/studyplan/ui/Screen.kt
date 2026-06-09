package com.example.studyplan.ui

sealed class Screen(val route: String) {
    data object NoteList : Screen("note_list")
    data object DueFlashcards : Screen("due_flashcards")
    data object AddNote : Screen("add_note")
    data object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Int) = "edit_note/$noteId"
    }

    data object ShowNote : Screen("show_note/{noteId}") {
        fun createRoute(noteId: Int) = "show_note/$noteId"
    }

    data object NoteCards : Screen("note_cards/{noteId}") {
        fun createRoute(noteId: Int) = "note_cards/$noteId"
    }

    data object ReviewSummary : Screen("review_summary/{noteId}") {
        fun createRoute(noteId: Int) = "review_summary/$noteId"
    }
}
