package com.example.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.Actor
import com.example.movieapp.data.remote.Movie
import com.example.movieapp.data.remote.api.MovieApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val client: MovieApi) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _actors = MutableStateFlow<List<Actor>>(emptyList())
    val actors: StateFlow<List<Actor>> = _actors

    private val _startMovie = MutableStateFlow<Movie?>(null)
    val startMovie: StateFlow<Movie?> = _startMovie
    private val _endMovie = MutableStateFlow<Movie?>(null)
    val endMovie: StateFlow<Movie?> = _endMovie

    val movieList = mutableListOf<Movie?>(null)
    val actorList = mutableListOf<Actor?>(null) // TODO: add getter/ setter

    init {
        chooseStartAndEndMovie()
    }

    private fun chooseStartAndEndMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            val startAndEndMovie = client.chooseStartAndEndMovie()
            val fetchedStartMovie = client.getMovieById(startAndEndMovie.startMovieId)
            val fetchedEndMovie = client.getMovieById(startAndEndMovie.endMovieId)

            _startMovie.emit(fetchedStartMovie)
            _endMovie.emit(fetchedEndMovie)
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedMovies = client.searchMovie(query)
            _movies.emit(fetchedMovies)
        }
    }

    fun searchActor(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedActors = client.searchActor(query)
            _actors.emit(fetchedActors.filter { actor -> actor.profileImageUrl != null })
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
}