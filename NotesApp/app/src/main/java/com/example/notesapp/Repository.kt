package com.example.notesapp

import com.example.notesapp.dao.NoteDao
import com.example.notesapp.entities.Notes

class Repository(private val noteDao: NoteDao) {

    suspend fun getNote():List<Notes>{
        return noteDao.getAllNotes()
    }

    suspend fun getIdNote(id:Int):Notes{
        return noteDao.getIdNotes(id)
    }

    suspend fun insertNote(note: Notes){
        return noteDao.insertNotes(note)
    }

    suspend fun updateNote(note: Notes){
        return noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Notes){
        return noteDao.deleteNote(note)
    }
}