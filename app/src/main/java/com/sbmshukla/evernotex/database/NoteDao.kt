package com.sbmshukla.evernotex.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sbmshukla.evernotex.model.Note

@Dao
interface NoteDao {
    //Create a note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    //Update a note
    @Update
    suspend fun updateNote(note: Note)

    //Delete a note
    @Delete
    suspend fun deleteNote(note: Note)

    //Get a all notes in descending order
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes():LiveData<List<Note>>

    //Search a note
    @Query("SELECT * FROM notes  WHERE title LIKE:query OR description Like:query")
    fun searchNote(query: String?):LiveData<List<Note>>
}