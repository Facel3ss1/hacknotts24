package com.example.movieapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.movieapp.data.remote.Actor
import com.example.movieapp.data.remote.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Game(
    name: String,
    startMovie: Movie,
    endMovie: Movie,
    screenController: NavHostController = rememberNavController()
) {
    Scaffold() { innerPadding ->
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
                    text = "target film: $endMovie"
                )

                NavHost(
                    navController = screenController,
                    "movie"
                ) {
                    composable("movie") {
                        FilmView(
                            movie = startMovie,
                            onNavigateToActor = { screenController.navigate("actor") },
                        )
                    }
                    composable("actor") {
                        ActorView(
                            actor = Actor(
                                2638,
                                "Cary Grant",
                                "https://image.tmdb.org/t/p/w185/oF5Vj64OEEDAy7DzpBP0W8fSwC6.jpg"
                            ),
                            onNavigateToMovie = { screenController.navigate("movie") },
                        )
                    }
                }
//                FilmView(
//                    movie = startMovie,
//                    endMovie = endMovie,
//                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                        .aspectRatio(27F / 40F)
                        .clip(RoundedCornerShape(15.dp))
                ) {
                    if (movie.posterImageUrl != null) {
                        AsyncImage(
                            model = movie.posterImageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
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
                    Text(text = "Submit")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorView(actor: Actor, onNavigateToMovie: () -> Unit) {
    val actors = listOf(
        Actor(
            2638,
            "Cary Grant",
            "https://image.tmdb.org/t/p/w185/oF5Vj64OEEDAy7DzpBP0W8fSwC6.jpg"
        )
    )
    Scaffold() { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                ) {
                    if (actor.profileImageUrl != null) {
                        AsyncImage(
                            model = actor.profileImageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
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
                    Text(text = "Submit")
                }

            }
//            LazyColumn {
//                items(actors.size) { actor ->
//
//                }
//            }
        }
    }
}