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
import coil.compose.AsyncImage
import com.example.movieapp.data.remote.Actor
import com.example.movieapp.data.remote.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Game(name: String, startMovie: Movie, endMovie: Movie) {
    Scaffold() { innerPadding ->
        Box(modifier = Modifier
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
                FilmView(
                    movie = startMovie,
                    endMovie = endMovie,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmView(movie: Movie, endMovie: Movie) {
    Scaffold() { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(27F / 40F)
                    .clip(RoundedCornerShape(15.dp))
                ) {
                    if(movie.posterImageURL != null) {
                        AsyncImage(
                            model = movie.posterImageURL,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    else {
                        Box(
                            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primaryContainer),
                        )
                    }
                }

                Text(textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    text = movie.toString()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorView(actor: Actor) {
    Scaffold() { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(27F / 40F)
                    .clip(CircleShape)
                ) {
                    if(actor.profileImageURL != null) {
                        AsyncImage(
                            model = actor.profileImageURL,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    else {
                        Box(
                            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primaryContainer),
                        )
                    }
                }

                Text(textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    text = actor.name
                )
            }
        }
    }
}