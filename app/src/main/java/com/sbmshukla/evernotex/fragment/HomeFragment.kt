package com.sbmshukla.evernotex.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sbmshukla.evernotex.MainActivity
import com.sbmshukla.evernotex.R
import com.sbmshukla.evernotex.adapter.NoteAdapter
import com.sbmshukla.evernotex.databinding.FragmentHomeBinding
import com.sbmshukla.evernotex.model.Note
import com.sbmshukla.evernotex.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener,
    MenuProvider {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel
        setUpRecyclerView()

        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }

    private fun updateUi(notes: List<Note>?) {
        if (notes != null) {
            if (notes.isNotEmpty()) {
                binding.emptyNotesImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            } else {
                binding.emptyNotesImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            noteViewModel.getAllNotes().observe(viewLifecycleOwner) {
                noteAdapter.differ.submitList(it)
                updateUi(it)
            }
        }
    }

    private fun searchNote(query: String?){
        val searchQuery="%$query"
        noteViewModel.searchNote(searchQuery).observe(this){
            noteAdapter.differ.submitList(it)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!=null)
        {
            searchNote(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding=null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu,menu)

        val menuSearch=menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled=false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       return false
    }
}