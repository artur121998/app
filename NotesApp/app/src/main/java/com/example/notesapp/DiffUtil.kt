package com.example.notesapp

import androidx.recyclerview.widget.DiffUtil
import com.example.notesapp.entities.Notes

class DiffUtil: DiffUtil.ItemCallback<Notes>() {


    override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem == newItem
    }
}

