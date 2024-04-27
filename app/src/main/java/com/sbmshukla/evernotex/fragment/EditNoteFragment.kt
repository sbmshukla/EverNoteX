package com.sbmshukla.evernotex.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.sbmshukla.evernotex.MainActivity
import com.sbmshukla.evernotex.R
import com.sbmshukla.evernotex.databinding.FragmentEditNoteBinding
import com.sbmshukla.evernotex.model.Note
import com.sbmshukla.evernotex.viewmodel.NoteViewModel


class EditNoteFragment : Fragment(R.layout.fragment_edit_note),MenuProvider {
    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var currentNote:Note

    private val args:EditNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost:MenuHost=requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote=args.note!!

        binding.editNoteTitle.setText(currentNote.title)
        binding.editNoteDesc.setText(currentNote.description)


        binding.editNoteFab.setOnClickListener {
            val noteTitle=binding.editNoteTitle.text.toString()
            val noteDescription=binding.editNoteDesc.text.toString()

            if (noteTitle.isNotEmpty()){
                val note=Note(currentNote.id,noteTitle, noteDescription,)
                noteViewModel.updateNote(note)

                Toast.makeText(context,"Note Updated", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }
            else{
                Toast.makeText(context,"Please enter note title", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete"){_,_->
                noteViewModel.deleteNote(currentNote)
                Toast.makeText(context,"Note deleted...", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_note,menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.deleteMenu->{
                deleteNote()
                true
            }
            else-> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding=null
    }
}