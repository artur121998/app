package com.example.notesapp.adapter

import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.notesapp.DiffUtil
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemRvViewBinding
import com.example.notesapp.entities.Notes
import com.example.notesapp.OnItemClickListener

class NotesAdapters :
    RecyclerView.Adapter<NotesAdapters.NotesViewHolder>() {

    private var listener: OnItemClickListener? = null
    private val diffUtilCallback = DiffUtil()
    private val listDiffer = AsyncListDiffer(this, diffUtilCallback)
    private lateinit var itemBinding: ItemRvViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        itemBinding = ItemRvViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    fun submitList(list: List<Notes>) {
        listDiffer.submitList(list)
    }

    fun setOnClickListener(listener1: OnItemClickListener?) {
        listener = listener1
    }

    class NotesViewHolder(
        private val binding: ItemRvViewBinding,
        private val listener: OnItemClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notes: Notes) {
            binding.tvText.text = notes.noteText
            binding.editTitle.text = notes.title
            binding.tvTextDateTime.text = notes.dataTime
            if (notes.color != null) {
                binding.cardView.setCardBackgroundColor(Color.parseColor(notes.color))
            } else {
                binding.cardView.setCardBackgroundColor(Color.parseColor("#171C26"))
            }
            if (notes.imgPath != null) {
                Glide.with(binding.imageNote.context)
                    .load(notes.imgPath)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            p0: GlideException?,
                            p1: Any?,
                            p2: Target<Drawable>?,
                            p3: Boolean
                        ): Boolean {
                            Log.e(TAG, "onLoadFailed ", p0)
                            for (t: Throwable in p0?.rootCauses!!) {
                                Log.e(TAG, "Caused by", t)
                            }
                            p0.logRootCauses(TAG)
                            return false
                        }

                        override fun onResourceReady(
                            p0: Drawable?,
                            p1: Any?,
                            p2: Target<Drawable>?,
                            p3: DataSource?,
                            p4: Boolean
                        ): Boolean {
                            Log.d(TAG, "OnResourceReady")
                            //do something when picture already loaded
                            return false
                        }
                    })
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.imageNote)
            }
            binding.cardView.setOnClickListener {
                listener?.onClicked(notes.id!!)
            }
        }
    }


}
