package com.sbmshukla.evernotex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sbmshukla.evernotex.databinding.NoteLayoutBinding
import com.sbmshukla.evernotex.fragment.HomeFragment
import com.sbmshukla.evernotex.fragment.HomeFragmentDirections
import com.sbmshukla.evernotex.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]
        holder.itemBinding.noteTitle.text = currentNote.title
        holder.itemBinding.noteDesc.text = currentNote.description
        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(direction)
        }
    }

    class NoteViewHolder(val itemBinding: NoteLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differenceCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id && oldItem.title == newItem.title && oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differenceCallback)
}