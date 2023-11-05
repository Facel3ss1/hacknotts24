package com.example.movieapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Movie(val id: Int, val title: String, val releaseYear: Int?, val posterImageURL: String?) {
    override fun toString(): String {
        return if (releaseYear != null) {
            "$title ($releaseYear)"
        } else {
            title
        }
    }
}
