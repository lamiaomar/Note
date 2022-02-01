package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repo.NoteRepo

class DeleteNoteUseCase(
    private val repo : NoteRepo
) {
    suspend operator fun invoke(note : Note){
        repo.deleteNote(note)
    }
}