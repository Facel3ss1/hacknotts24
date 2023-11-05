package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.theme.MovieAppTheme
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    MyAppNavHost(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)


@Composable
fun MyAppNavHost(modifier: Modifier = Modifier,
                 navController: NavHostController = rememberNavController(),
                 startDestination: String = "profile",)  {
    MovieAppTheme {
        NavHost(modifier = modifier, navController = navController, startDestination = "menu") {
            composable("menu") {
                Menu(
                    Message("Android", "Jetpack Compose"),
                    onNavigateToSearchMovies = { navController.navigate("searchmovies") },
                    onNavigateToRandomMovies = { navController.navigate("randommovies") },
                    onNavigateToSettings = { navController.navigate("settings") },
                    onNavigateToCredits = { navController.navigate("theCredits") },
                    onNavigateToQuit = { navController.navigate("quit") }
                )
            }
            composable("searchmovies") {
                SearchMovies()
            }
            composable("randommovies") {
                RandomMovies()
            }
            composable("settings") {
                Settings()
            }
            composable("theCredits") {
                Credits()
            }
        }
            /*...*/
    }
//    Surface(color = Color.Cyan) {
//        Text(
//            text = "Hi, my name is $name!",
//            modifier = modifier.padding(24.dp)
//        )
//    }

}

@Composable
fun Settings() {

}

@Composable
fun RandomMovies() {

}
@Composable
fun Credits() {
    Box(contentAlignment = Alignment.Center) {
        Column {
            Text(
                color = MaterialTheme.colorScheme.secondary,
                text = "Coding - Peter Medus"
            )
            Text(
                color = MaterialTheme.colorScheme.secondary,
                text = "Coding - Charlie Baker"
            )
        }
    }
}

@Composable
fun SearchMovies() {

}


@Composable
fun Menu(
    msg: Message,
    onNavigateToSearchMovies: () -> Unit,
    onNavigateToRandomMovies: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCredits: () -> Unit,
    onNavigateToQuit: () -> Unit,
) {
    Box(contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
//        Image(
//            painter = painterResource(R.drawable.profile_picture),
//            contentDescription = "Contact profile picture",
//        )
            Column {
                Text(
                    color = MaterialTheme.colorScheme.secondary,
                    text = msg.author
                )
                Text(
                    text = msg.body
                )
                Column() {
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
                    Button(onClick = onNavigateToQuit, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Quit")
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    MovieAppTheme {
        Column {
            Menu(
                msg = Message("Lexi", "Hey, take a look at Jetpack Compose, it's great!"),
                onNavigateToSearchMovies = {},
                onNavigateToRandomMovies = {},
                onNavigateToCredits = {},
                onNavigateToSettings = {},
                onNavigateToQuit = {}
            )
        }
    }
}