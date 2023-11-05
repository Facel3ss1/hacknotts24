package com.example.movieapp.data.remote.api

import com.example.movieapp.data.remote.Actor
import com.example.movieapp.data.remote.Movie
import com.example.movieapp.data.remote.StartAndEndMovie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Inject

class KtorMovieApiClient @Inject constructor() : MovieApi {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
//        install(DefaultRequest)
        install(Logging)
        install(HttpCache)
//        defaultRequest {
//            url {
//                protocol = URLProtocol.HTTPS
//                host = "movie-finding-game.fly.dev"
//            }
//            headers.appendIfNameAbsent(HttpHeaders.ContentType, ContentType.Application.Json)
//        }
    }

    override suspend fun chooseStartAndEndMovie(): StartAndEndMovie {
        val url = URLBuilder().apply {
            protocol = URLProtocol.HTTPS
            host = "movie-finding-game.fly.dev"
            appendPathSegments("movie", "chooseStartAndEnd")
        }

        return httpClient.get(url.buildString()).body()
    }

    override suspend fun getMovieById(id: Int): Movie {
        val url = URLBuilder().apply {
            protocol = URLProtocol.HTTPS
            host = "movie-finding-game.fly.dev"
            appendPathSegments("movie", id.toString())
        }

        return httpClient.get(url.buildString()).body()
    }

    override suspend fun searchMovie(query: String): List<Movie> {
        val url = URLBuilder().apply {
            protocol = URLProtocol.HTTPS
            host = "movie-finding-game.fly.dev"
            path("movie/search")
            parameters.append("query", query)
        }

        return httpClient.get(url.buildString()).body()
    }

    override suspend fun searchActor(query: String): List<Actor> {
        val url = URLBuilder().apply {
            protocol = URLProtocol.HTTPS
            host = "movie-finding-game.fly.dev"
            path("actor/search")
            parameters.append("query", query)
        }

        return httpClient.get(url.buildString()).body()
    }
}