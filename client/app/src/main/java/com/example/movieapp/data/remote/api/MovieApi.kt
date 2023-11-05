package com.example.movieapp.data.remote.api

import com.example.movieapp.data.remote.Actor
import com.example.movieapp.data.remote.Movie
import com.example.movieapp.data.remote.StartAndEndMovie

interface MovieApi {
    suspend fun chooseStartAndEndMovie(): StartAndEndMovie
    suspend fun getMovieById(id: Int): Movie
    suspend fun searchMovie(query: String): List<Movie>
    suspend fun searchActor(query: String): List<Actor>
}