package com.example.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.Movie
import com.example.movieapp.data.remote.api.KtorApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(private val client: KtorApiClient) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private fun searchMovie(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedMovies = client.searchMovie(query)
            _movies.emit(fetchedMovies)
        }
    }
}