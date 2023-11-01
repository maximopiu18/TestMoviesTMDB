package com.example.themoviewdbapptest.ui.movies.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviewdbapptest.data.repository.MoviesApiRepository
import com.example.themoviewdbapptest.ui.movies.viewmodel.MoviesApiViewModel

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory(private val moviesApiRepository: MoviesApiRepository, private val moviesApiViewModel: MoviesApiViewModel.MoviesDataCallback) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MoviesApiViewModel::class.java)) {
            MoviesApiViewModel(this.moviesApiRepository, moviesApiViewModel) as T
        } else throw IllegalArgumentException("No se encontró la clase")
    }
}
