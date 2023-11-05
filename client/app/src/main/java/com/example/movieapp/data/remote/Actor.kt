package com.example.movieapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Actor(val id: Int, val name: String, val profileImageURL: String?)
