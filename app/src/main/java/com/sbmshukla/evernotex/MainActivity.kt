package com.sbmshukla.evernotex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sbmshukla.evernotex.database.NoteDatabase
import com.sbmshukla.evernotex.databinding.ActivityMainBinding
import com.sbmshukla.evernotex.repository.NoteRepository
import com.sbmshukla.evernotex.viewmodel.NoteViewModel
import com.sbmshukla.evernotex.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = ViewModelFactory(application, noteRepository)
        noteViewModel=ViewModelProvider(this,viewModelProviderFactory)[NoteViewModel::class.java]
    }
}