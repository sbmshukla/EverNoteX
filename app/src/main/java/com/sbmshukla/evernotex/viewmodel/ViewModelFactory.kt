package com.sbmshukla.evernotex.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sbmshukla.evernotex.repository.NoteRepository

class ViewModelFactory(private val application: Application, private val repository: NoteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(application,repository) as T
    }
}