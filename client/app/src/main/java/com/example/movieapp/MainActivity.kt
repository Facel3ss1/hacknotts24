package com.example.movieapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.ui.MoviesViewModel
import com.example.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun App() {
    MovieAppTheme {
        AppNavHost()
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") {
            Menu(
                "Movie Finding Game",
                onNavigateToRandomMovies = { navController.navigate("randomMovies") },
                onNavigateToCredits = { navController.navigate("credits") },
            )
        }
        composable("randomMovies") {
            RandomMovies()
        }
        composable("credits") {
            Credits()
        }
    }
}

@Composable
fun Menu(
    heading: String,
    onNavigateToRandomMovies: () -> Unit,
    onNavigateToCredits: () -> Unit,
) {
    Scaffold { innerPadding ->
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
        ) {
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
                        Button(
                            onClick = onNavigateToRandomMovies,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Play with random movies")
                        }
                        Button(onClick = onNavigateToCredits, modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Credits")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RandomMovies(viewModel: MoviesViewModel = hiltViewModel()) {
    val startMovie by viewModel.startMovie.collectAsState()
    val endMovie by viewModel.endMovie.collectAsState()
    Game(name = "Random", startMovie, endMovie = endMovie, viewModel)
}