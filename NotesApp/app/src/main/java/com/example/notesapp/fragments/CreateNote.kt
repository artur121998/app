package com.example.notesapp.fragments

import android.content.*
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.notesapp.IntentTitle
import com.example.notesapp.NoteViewModel
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentCreateNoteBinding
import com.example.notesapp.entities.Notes
import java.text.SimpleDateFormat
import java.util.*

class CreateNote : Fragment() {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding
    private lateinit var textViewDate: TextView
    private lateinit var imgBack: ImageView
    private lateinit var imgSave: ImageView
    private lateinit var imgMore: ImageView
    private lateinit var imgDelete: ImageView
    private lateinit var img: ImageView
    private lateinit var colorView: View
    private lateinit var editTitle: EditText
    private lateinit var editSubTitle: EditText
    private lateinit var editText: EditText
    private lateinit var date: String
    private var note: Notes? = null
    private lateinit var viewModel: NoteViewModel
    private var selectedColor = "#4e33ff"
    private var noteId = -1
    private var selectedImg: String? = null

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intentAction(intent!!)
        }
    }



    private val pickImage: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                setImage(it.toString())
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            noteId = arguments?.getString("ID")?.toInt()!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        initParam()
        if (noteId != -1) {
            viewModel(noteId)
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver,
            IntentFilter(IntentTitle.bottomSheetAction)
        )

        currentDate()
        listener()

        return binding!!.root
    }

    private fun viewModel(noteId: Int) {
        viewModel.getIdNote(noteId)
        viewModel.noteId.observe(this) {
            note = it
            editTitle.setText(it.title)
            editSubTitle.setText(it.subTitle)
            editText.setText(it.noteText)
            if (it.imgPath != null) {
                setImage(it.imgPath!!)
                selectedImg = it.imgPath
            }
            colorView.setBackgroundColor(Color.parseColor(it.color))
            selectedColor = it.color.toString()
        }
    }


    private fun initParam() {
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        textViewDate = binding?.tvDataTime!!
        imgBack = binding?.imgBack!!
        imgSave = binding?.imgDone!!
        imgMore = binding?.imgMore!!
        imgDelete = binding?.imgDelete!!
        img = binding?.img!!
        editTitle = binding?.editTitle!!
        editSubTitle = binding?.editSubTitle!!
        editText = binding?.etText!!
        colorView = binding?.colorView!!
    }

    private fun setImage(it: String) {
        selectedImg = it
        binding?.layoutImage!!.visibility = View.VISIBLE
        Glide.with(img.context)
            .load(it)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    p0: GlideException?,
                    p1: Any?,
                    p2: Target<Drawable>?,
                    p3: Boolean
                ): Boolean {
                    Log.e(ContentValues.TAG, "onLoadFailed ", p0)
                    for (t: Throwable in p0?.rootCauses!!) {
                        Log.e(ContentValues.TAG, "Caused by", t)
                    }
                    p0.logRootCauses(ContentValues.TAG)
                    return false
                }

                override fun onResourceReady(
                    p0: Drawable?,
                    p1: Any?,
                    p2: Target<Drawable>?,
                    p3: DataSource?,
                    p4: Boolean
                ): Boolean {
                    Log.d(ContentValues.TAG, "OnResourceReady")
                    //do something when picture already loaded
                    return false
                }
            })
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(img)
    }

    private fun currentDate() {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
        date = sdf.format(Date())
        textViewDate.text = date
    }

    private fun listener() {
        imgBack.setOnClickListener {
            back()
        }
        imgSave.setOnClickListener {
            saveNote()
        }
        imgMore.setOnClickListener {
            bottom()
        }
        imgDelete.setOnClickListener {
            selectedImg = null
            binding?.layoutImage!!.visibility = View.GONE
        }
    }

    private fun bottom() {
        val bottom: NoteBottomSheetFragment = if (note != null) {
            NoteBottomSheetFragment.newInstance(note?.id!!)
        } else {
            NoteBottomSheetFragment.newInstance(-1)
        }
        bottom.show(requireActivity().supportFragmentManager, "note bottom sheet fragment")

    }

    private fun back() {
        findNavController().navigate(R.id.homeFragment)
    }

    private fun saveNote() {
        if (!checkIsNullOrEmpty()) {
            if (note != null) {
                viewModel.updateNote(getNote(note!!))
                clearText()
                back()
            } else {
                val notes = Notes()
                viewModel.saveNote(getNote(notes))
                clearText()
                back()
            }
        }
    }

    private fun checkIsNullOrEmpty(): Boolean {
        var isCheck = false
        if (editTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Требуется заголовок", Toast.LENGTH_SHORT).show()
            isCheck = true
        }
        if (editText.text.isNullOrEmpty()) {
            Toast.makeText(context, "Требуется текст", Toast.LENGTH_SHORT).show()
            isCheck = true
        }
        return isCheck
    }

    private fun getNote(notes: Notes): Notes {

        notes.title = editTitle.text.toString()
        notes.subTitle = editSubTitle.text.toString()
        notes.noteText = editText.text.toString()
        notes.dataTime = date
        notes.color = selectedColor
        notes.imgPath = selectedImg
        return notes
    }

    private fun clearText() {
        editTitle.setText("")
        editSubTitle.setText("")
        editText.setText("")
    }

    private fun intentAction(intent:Intent){
        when (intent.getStringExtra(IntentTitle.type)) {
            IntentTitle.color -> {
                selectedColor = intent.getStringExtra(IntentTitle.selectedValue).toString()
                colorView.setBackgroundColor(Color.parseColor(selectedColor))
            }
            IntentTitle.image -> {
                pickImage.launch("image/*")
            }
            IntentTitle.delete -> {
                viewModel.deleteNote(note!!)
                back()
            }
            else -> {}
        }
    }

    override fun onDestroyView() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
        _binding = null
        super.onDestroyView()

    }


}