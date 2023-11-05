package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(modifier = modifier, navController = navController, startDestination = "menu") {
        composable("menu") {
            Menu(
                "Movie Finding Game",
                onNavigateToSearchMovies = { navController.navigate("searchMovies") },
                onNavigateToRandomMovies = { navController.navigate("randomMovies") },
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToCredits = { navController.navigate("credits") },
            )
        }
        composable("searchMovies") {
            SearchMovies()
        }
        composable("randomMovies") {
            RandomMovies()
        }
        composable("settings") {
            Settings()
        }
        composable("credits") {
            Credits()
        }
    }
}


@Composable
fun Settings() {
    Text(
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.primary,
        text = "Settings"
    )
}

@Composable
fun RandomMovies() {
    Text(
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.primary,
        text = "Random"
    )
}

@Composable
fun Credits() {
    Box(contentAlignment = Alignment.Center) {
        Column {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                text = "Credits"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                text = "Coding - Peter Medus"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                text = "Coding - Charlie Baker"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                text = "This application uses TMDB and the TMDB APIs but is not endorsed, certified, or otherwise approved by TMDB."
            )
        }
    }
}

@Composable
fun SearchMovies() {
    Text(
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.primary,
        text = "Search"
    )
}

@Composable
fun Menu(
    heading: String,
    onNavigateToSearchMovies: () -> Unit,
    onNavigateToRandomMovies: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCredits: () -> Unit,
) {
    Box(contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Column {
                Text(
                    fontWeight = Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    text = heading,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Column {
                    Button(onClick = onNavigateToSearchMovies, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Play with specific movies")
                    }
                    Button(onClick = onNavigateToRandomMovies, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Play with random movies")
                    }
                    Button(onClick = onNavigateToSettings, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Settings")
                    }
                    Button(onClick = onNavigateToCredits, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Credits")
                    }
                }
            }
        }
    }
}