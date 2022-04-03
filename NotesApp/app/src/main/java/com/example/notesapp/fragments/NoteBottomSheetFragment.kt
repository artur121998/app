package com.example.notesapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notesapp.IntentTitle
import com.example.notesapp.R
import com.example.notesapp.databinding.BottomViewFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NoteBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomViewFragmentBinding? = null
    private val binding get() = _binding!!
    private var selectedColor = "#171C26"
    private var noteId = -1
    private lateinit var listImageNote: MutableList<ImageView>

    companion object {
        fun newInstance(notesId: Int): NoteBottomSheetFragment {
            val args = Bundle()
            args.putInt("ID", notesId)
            val fragment = NoteBottomSheetFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            noteId = arguments?.getInt("ID")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomViewFragmentBinding.inflate(inflater, container, false)
        init()
        setListener()
        return binding.root
    }

    private fun init() {
        if (noteId != -1) {
            binding.layoutDelete.visibility = View.VISIBLE
        } else {
            binding.layoutDelete.visibility = View.GONE
        }

        listImageNote = mutableListOf(
            binding.imgNote1,
            binding.imgNote2,
            binding.imgNote3,
            binding.imgNote4,
            binding.imgNote5,
            binding.imgNote6,
            binding.imgNote7
        )

    }

    private fun MutableList<ImageView>.setColor(index: Int) {
        this.indices.forEach {
            if (it != index) {
                this[it].setImageResource(0)
            } else {
                this[it].setImageResource(R.drawable.ic_baseline_done_24)
            }
        }
    }

    private fun setListener() {

        binding.fNote1.setOnClickListener {
            listImageNote.setColor(0)
            selectedColor = "#4e33ff"
            val intent = putExtra(IntentTitle.color, selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.fNote2.setOnClickListener {
            listImageNote.setColor(1)
            selectedColor = "#ffd633"
            val intent = putExtra(IntentTitle.color, selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }

        binding.fNote3.setOnClickListener {
            listImageNote.setColor(2)
            selectedColor = "#CACACA"
            val intent = putExtra(IntentTitle.color, selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.fNote4.setOnClickListener {
            listImageNote.setColor(3)
            selectedColor = "#ffffff"
            val intent = putExtra(IntentTitle.color, selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.fNote5.setOnClickListener {
            listImageNote.setColor(4)
            selectedColor = "#ae3b76"
            val intent = putExtra(IntentTitle.color, selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.fNote6.setOnClickListener {
            listImageNote.setColor(5)
            selectedColor = "#0aebaf"
            val intent = putExtra(IntentTitle.color, selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.fNote7.setOnClickListener {
            listImageNote.setColor(6)
            selectedColor = "#ff7746"
            val intent = putExtra(IntentTitle.color, selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.layoutImage.setOnClickListener {
            val intent = putExtra(IntentTitle.image)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }

        binding.layoutDelete.setOnClickListener {
            val intent = putExtra(IntentTitle.delete)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }

    }

    private fun putExtra(type: String, selectedValue: String): Intent {
        val intent = Intent(IntentTitle.bottomSheetAction)
        intent.putExtra(IntentTitle.type, type)
        intent.putExtra(IntentTitle.selectedValue, selectedValue)
        return intent
    }

    private fun putExtra(type: String): Intent {
        val intent = Intent(IntentTitle.bottomSheetAction)
        intent.putExtra(IntentTitle.type, type)
        return intent
    }

}