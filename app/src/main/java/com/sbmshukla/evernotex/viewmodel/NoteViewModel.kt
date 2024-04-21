package com.sbmshukla.evernotex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.sbmshukla.evernotex.model.Note
import com.sbmshukla.evernotex.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application, private val repository: NoteRepository) :
    AndroidViewModel(application) {

    fun addNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun getAllNotes() = repository.getAllNotes()

    fun searchNote(query: String)=repository.searchNote(query)
}