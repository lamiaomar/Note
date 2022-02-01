package com.example.noteapp.feature_note.domain.repo

import androidx.compose.foundation.lazy.rememberLazyListState
import com.example.noteapp.feature_note.data.data_soruce.NoteDao
import com.example.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRpoImpl(
    private val dao : NoteDao
) : NoteRepo {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note)
    }
}