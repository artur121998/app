package com.example.notesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.NoteViewModel
import com.example.notesapp.R
import com.example.notesapp.adapter.NotesAdapters
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.OnItemClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var fabCreteNote: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private  var notesAdapter: NotesAdapters= NotesAdapters()
    private lateinit var viewModel: NoteViewModel
    private val onClicked = object :OnItemClickListener{
        override fun onClicked(id:Int) {
           val args=Bundle()
            args.putString("ID",id.toString())
            findNavController().navigate(R.id.createNote,args)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        setupRecyclerView()
        setListener()
        viewModel()

        return binding.root
    }

    private fun init() {
        fabCreteNote = binding.fabAddNote
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recView
        notesAdapter.setOnClickListener(listener1 = onClicked)
        val adapter=notesAdapter

        recyclerView.apply {
            this.adapter=adapter
            this.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    private fun setListener() {
        fabCreteNote.setOnClickListener {
            findNavController().navigate(R.id.createNote,null)
        }
    }

    private fun viewModel() {
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        viewModel.getAllNote()
        viewModel.note.observe(this, Observer {
            if (it.isNotEmpty()) {
                notesAdapter.submitList(it)
            }
        })
    }


    override fun onDestroyView() {
        _binding=null
        super.onDestroyView()

    }
}