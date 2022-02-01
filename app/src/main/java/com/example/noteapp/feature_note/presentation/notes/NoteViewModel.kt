package com.example.noteapp.feature_note.presentation.notes

import androidx.lifecycle.ViewModel
import com.example.noteapp.feature_note.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
) : ViewModel() {


    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentlyDeletedNote: Note? = null

   private var getNoteJob : Job? = null

   init {
       getNotes(NoteOrder.Date(OrderType.Descending))
   }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
               getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCase.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCase.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }

            }
        }
    }

   private fun getNotes(noteOrder: NoteOrder) {
      getNoteJob?.cancel()
      getNoteJob = noteUseCase.getNotesUseCase(noteOrder)
         .onEach { notes ->
            _state.value = state.value.copy(
               notes = notes,
               noteOrder = noteOrder
            )
         }
         .launchIn(viewModelScope)
   }
}