package com.example.e_times

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.e_times.models.Article
import com.example.e_times.screens.*
import com.example.e_times.ui.theme.ETimesTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//             Remember a SystemUiController
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight

            SideEffect {
                // Update all of the system bar colors to be transparent, and use
                // dark icons if we're in light theme
                systemUiController.setNavigationBarColor(
                    color = Color.Black,
                    darkIcons = useDarkIcons,
                )

                // setStatusBarsColor() and setNavigationBarsColor() also exist
            }
            ETimesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.NewsScreen.route ){

        composable(Screen.NewsScreen.route){
            NewsScreen(navController = navController)
        }

        composable(Screen.FavoritesScreen.route){
            FavoritesScreen(navController = navController)
        }

        composable(Screen.SearchScreen.route){
            SearchScreen(navController =navController)
        }
        
        composable(Screen.WebViewScreen.route+"/{article}", arguments = listOf(
            navArgument("article"){
                type = NavType.StringType
            }
        )){ entry ->
            val article = Gson().fromJson(entry.arguments?.getString("article"), Article::class.java)
            WebViewScreen(navController = navController, article = article)
        }

    }

}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ETimesTheme {
//        Greeting("Android")
//    }
//}