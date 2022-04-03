package com.example.notesapp.dao

import androidx.room.*
import com.example.notesapp.entities.Notes
import com.example.notesapp.entities.NotesName

@Dao
interface NoteDao {

    @Query("SELECT * FROM ${NotesName.tableName} ORDER BY id DESC")
    suspend fun getAllNotes(): List<Notes>

    @Query("SELECT * FROM ${NotesName.tableName} WHERE id =:id ")
    suspend fun getIdNotes(id:Int): Notes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Update
    suspend fun updateNote(note: Notes)
}