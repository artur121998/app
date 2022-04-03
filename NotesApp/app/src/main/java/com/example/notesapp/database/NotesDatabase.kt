package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.dao.NoteDao
import com.example.notesapp.entities.Notes
import com.example.notesapp.entities.NotesName

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    companion object {
        @Volatile                                                                                    //гарантируют, что считываемое значение поступает из основной памяти, а не из кэша процессора,
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            val tempDatabase = INSTANCE
            if (tempDatabase != null) {
                return tempDatabase
            }
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context,
                        NotesDatabase::class.java,
                        NotesName.tableName + ".db"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
        }
    }

    abstract fun noteDao(): NoteDao
}