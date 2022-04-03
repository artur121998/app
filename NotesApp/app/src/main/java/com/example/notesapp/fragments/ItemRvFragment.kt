package com.example.notesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemRvViewBinding

class ItemRvFragment:Fragment(R.layout.item_rv_view) {

    private var _binding: ItemRvViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= ItemRvViewBinding.inflate(inflater,container,false)
        return binding.root
    }
}