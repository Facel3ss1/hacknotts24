package com.example.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.Actor
import com.example.movieapp.data.remote.Movie
import com.example.movieapp.data.remote.api.KtorApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(private val client: KtorApiClient) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _actors = MutableStateFlow<List<Actor>>(emptyList())
    val actors: StateFlow<List<Actor>> = _actors

    private val _startMovie = MutableStateFlow<Movie?>(null)
    private val _endMovie = MutableStateFlow<Movie?>(null)

    private fun searchMovie(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedMovies = client.searchMovie(query)
            _movies.emit(fetchedMovies)
        }
    }

    private fun setStartMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedMovie = client.getMovieById(id)
            _startMovie.emit(fetchedMovie)
        }
    }

    private fun setEndMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedMovie = client.getMovieById(id)
            _endMovie.emit(fetchedMovie)
        }
    }

//    companion object {
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//                return MoviesViewModel(KtorApiClient()) as T
//            }
//        }
//    }
}