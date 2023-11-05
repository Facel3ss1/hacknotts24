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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.movieapp.data.remote.Movie
import com.example.movieapp.ui.MoviesViewModel

@Composable
fun Game(
    name: String,
    startMovie: Movie?,
    endMovie: Movie?,
    viewModel: MoviesViewModel,
    screenController: NavHostController = rememberNavController()
) {
    Scaffold() { innerPadding ->
        if (startMovie == null || endMovie == null) {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
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
                        "movie"
                    ) {
                        composable("movie") {
                            FilmView(
                                movie = startMovie,
                                onNavigateToActor = { screenController.navigate("searchActor") },
                            )
                        }
                        composable("searchActor") {
                            SearchActor(viewModel)
                        }
                        composable("actor") {
                            ActorView()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActorView() {
    Scaffold() { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .aspectRatio(1F)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Composable
fun FilmView(movie: Movie, onNavigateToActor: () -> Unit) {
    Scaffold() { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
}

@Composable
fun SearchActor(viewModel: MoviesViewModel) {
    val actors by viewModel.actors.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }

    Scaffold() { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(singleLine = true, value = query, onValueChange = {
                    query = it
                    if (query.isNotEmpty()) {
                        viewModel.searchActor(query)
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
                                    onClick = { },
                                )
                            }
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }
    }
}