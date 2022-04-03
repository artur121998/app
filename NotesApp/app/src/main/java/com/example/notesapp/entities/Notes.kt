package com.example.notesapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = NotesName.tableName)
data class Notes(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = NotesName.title)
    var title: String? = null,

    @ColumnInfo(name = NotesName.subTitle)
    var subTitle: String? = null,

    @ColumnInfo(name = NotesName.dataTime)
    var dataTime: String? = null,

    @ColumnInfo(name = NotesName.noteText)
    var noteText: String? = null,

    @ColumnInfo(name = NotesName.imgPath)
    var imgPath: String? = null,

    @ColumnInfo(name = NotesName.webLink)
    var webLink: String? = null,

    @ColumnInfo(name = NotesName.color)
    var color: String? = null

) : Serializable