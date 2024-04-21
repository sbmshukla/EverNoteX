package com.sbmshukla.evernotex.repository

import androidx.room.Query
import com.sbmshukla.evernotex.database.NoteDatabase
import com.sbmshukla.evernotex.model.Note

class NoteRepository(private val db:NoteDatabase) {
    suspend fun insertNote(note: Note)=db.getNoteDao().insertNote(note)
    suspend fun deleteNote(note: Note)=db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Note)=db.getNoteDao().insertNote(note)

    fun getAllNotes()=db.getNoteDao().getAllNotes()
    fun searchNote(query:String)=db.getNoteDao().searchNote(query)
}