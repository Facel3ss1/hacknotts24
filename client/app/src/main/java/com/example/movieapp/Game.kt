package com.example.movieapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.movieapp.data.remote.Actor
import com.example.movieapp.data.remote.Movie
import com.example.movieapp.ui.MoviesViewModel

@Composable
fun Game(
    name: String,
    viewModel: MoviesViewModel = hiltViewModel(),
    screenController: NavHostController = rememberNavController()
) {
    val startMovie by viewModel.startMovie.collectAsState()
    val endMovie by viewModel.endMovie.collectAsState()

    viewModel.movieList.add(startMovie)

    Scaffold() { innerPadding ->
        if (startMovie == null || endMovie == null) {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                text = "Loading..."
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                        text = name
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        text = "Target Film: $endMovie"
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    NavHost(
                        navController = screenController,
                        startDestination = "movie"
                    ) {
                        composable("movie") {
                            val movie = viewModel.movieList[viewModel.movieList.lastIndex]
                            if (movie != null) {
                                FilmView(
                                    movie = movie,
                                    onNavigateToActor = { screenController.navigate("searchActor") },
                                )
                            }
                        }
                        composable("searchActor") {
                            val actors by viewModel.actors.collectAsState()
                            SearchActor(
                                actors = actors,
                                onQueryChanged = { query -> viewModel.searchActor(query) },
                                onActorClicked = { screenController.navigate("actor") },
                                viewModel = viewModel,
                            )
                        }
                        composable("actor") {
                            val actor = viewModel.actorList[viewModel.actorList.lastIndex]
//                                .collectAsState()
                            if (actor != null) {
                                ActorView(
                                    actor = actor,
                                    onNavigateToMovie = { screenController.navigate("searchMovie") }
                                )
                            }
                        }
                        composable("searchMovie") {
                            val movies by viewModel.movies.collectAsState()
                            SearchMovie(
                                movies = movies,
                                onQueryChanged = { query -> viewModel.searchMovie(query) },
                                onMovieClicked = { screenController.navigate("movie") },
                                viewModel = viewModel,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActorView(actor: Actor, onNavigateToMovie: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(15.dp))
//                    .fillMaxWidth(0.5f)
//                    .aspectRatio(1F)
//                    .clip(CircleShape)
            ) {
                if (actor.profileImageUrl != null) {
                    AsyncImage(
                        model = actor.profileImageUrl,
                        null,
                        modifier = Modifier.fillMaxSize()
                )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primaryContainer),
                    )
                }
            }

            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                text = actor.name
            )

            Button(onClick = onNavigateToMovie) {
                Text(text = "Next")
            }
        }
    }
}

@Composable
fun FilmView(movie: Movie, onNavigateToActor: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(15.dp))
            ) {
                if (movie.posterImageUrl != null) {
                    AsyncImage(
                        model = movie.posterImageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primaryContainer),
                    )
                }
            }

            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                text = movie.toString()
            )

            Button(onClick = onNavigateToActor) {
                Text(text = "Next")
            }
        }
    }
}

@Composable
fun SearchMovie(
    movies: List<Movie>,
    onQueryChanged: (String) -> Unit,
    onMovieClicked: (Movie) -> Unit,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    var query by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(singleLine = true, value = query, onValueChange = {
                query = it
                if (query.isNotEmpty()) {
                    onQueryChanged(query)
                }
            })
            Spacer(modifier = Modifier.padding(8.dp))
            Box {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(
                        if (query.isEmpty()) {
                            0
                        } else {
                            movies.size
                        }
                    ) { movie ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = movies[movie].posterImageUrl,
                                contentDescription = null,
                            )
                            TextButton(
                                content = { Text(movies[movie].title) },
                                onClick = {
                                    onMovieClicked(movies[movie]);
                                    viewModel.movieList.add(movies[movie]) //TODO: Add Validation
                                },
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SearchActor(
    actors: List<Actor>,
    onQueryChanged: (String) -> Unit,
    onActorClicked: (Actor) -> Unit,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    var query by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(singleLine = true, value = query, onValueChange = {
                query = it
                if (query.isNotEmpty()) {
                    onQueryChanged(query)
                }
            })
            Spacer(modifier = Modifier.padding(8.dp))
            Box {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(
                        if (query.isEmpty()) {
                            0
                        } else {
                            actors.size
                        }
                    ) { actor ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = actors[actor].profileImageUrl,
                                contentDescription = null,
                            )
                            TextButton(
                                content = { Text(actors[actor].name) },
                                onClick = {
                                    onActorClicked(actors[actor]);
                                    viewModel.actorList.add(actors[actor]) //TODO: Add Validation
                                },
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}