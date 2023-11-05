package com.example.movieapp.data.remote.api

import com.example.movieapp.data.remote.Actor
import com.example.movieapp.data.remote.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json

class KtorApiClient {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            install(ContentNegotiation) {
                json()
            }
        }
        install(DefaultRequest)
//        install(Logging)
        install(HttpCache)
        defaultRequest {
            url("https://movie-finding-game.fly.dev/")
        }
    }

    suspend fun getMovieById(id: Int): Movie {
        val url = URLBuilder().apply {
            appendPathSegments("movie", id.toString())
        }

        return httpClient.get(url.buildString()).body()
    }

    suspend fun searchMovie(query: String): List<Movie> {
        val url = URLBuilder().apply {
            path("movie/search")
            parameters.append("query", query)
        }

        return httpClient.get(url.buildString()).body()
    }

    suspend fun searchActor(query: String): List<Actor> {
        val url = URLBuilder().apply {
            path("actor/search")
            parameters.append("query", query)
        }

        return httpClient.get(url.buildString()).body()
    }
}