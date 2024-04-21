package com.sbmshukla.evernotex.fragment

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
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
import com.sbmshukla.evernotex.MainActivity
import com.sbmshukla.evernotex.R
import com.sbmshukla.evernotex.adapter.NoteAdapter
import com.sbmshukla.evernotex.databinding.FragmentAddNoteBinding
import com.sbmshukla.evernotex.databinding.FragmentHomeBinding
import com.sbmshukla.evernotex.viewmodel.NoteViewModel

class AddNoteFragment : Fragment(R.layout.fragment_add_note),MenuProvider {

    private var addNoteBinding: FragmentAddNoteBinding? = null
    private val binding get() = addNoteBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var addNoteView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel
        addNoteView=view
    }

    private fun saveNote(view: View){
        val noteTitle=binding.addNoteTitle.text.toString().trim()
        val noteDescription=binding.addNoteDesc.text.toString().trim()

        if (noteTitle.isNotEmpty()){
            val note= com.sbmshukla.evernotex.model.Note(0, noteTitle, noteDescription)
            noteViewModel.addNote(note)

            Toast.makeText(addNoteView.context,"Note added", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)
        }
        else{
            Toast.makeText(addNoteView.context,"Please enter note title", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.add_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.saveMenu->{
                saveNote(addNoteView)
                true
            }
            else-> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding=null
    }

}