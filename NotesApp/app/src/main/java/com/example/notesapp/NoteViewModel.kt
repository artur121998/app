package com.example.notesapp

import android.app.Application
import androidx.lifecycle.*
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.entities.Notes
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) :
    AndroidViewModel(application) {                         //используем AVM если нужен context
    private val _note = MutableLiveData<List<Notes>>()
    private val _noteId = MutableLiveData<Notes>()
    val note: LiveData<List<Notes>>
        get() = _note

    val noteId: LiveData<Notes>
        get() = _noteId

    private lateinit var list: List<Notes>

    private var noteRepository = Repository(NotesDatabase.getDatabase(application).noteDao())

    fun getAllNote() {
        viewModelScope.launch {
            list = noteRepository.getNote()
            _note.postValue(list)
        }
    }

    fun getIdNote(id: Int) {
        viewModelScope.launch {
            val notes = noteRepository.getIdNote(id)
            _noteId.postValue(notes)
        }
    }

    fun saveNote(note: Notes) {
        viewModelScope.launch {
            noteRepository.insertNote(note)
            getAllNote()
        }
    }

    fun updateNote(note: Notes) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
            getAllNote()
        }
    }

    fun deleteNote(note: Notes) {

        viewModelScope.launch {

                noteRepository.deleteNote(note)
                getAllNote()

        }
    }
}