package com.example.movieapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class StartAndEndMovie(val startMovieId: Int, val endMovieId: Int)
