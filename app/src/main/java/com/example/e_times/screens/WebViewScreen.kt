package com.example.e_times.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.e_times.NewsViewModel
import com.example.e_times.Toolbar
import com.example.e_times.models.Article
import com.example.e_times.models.News
import kotlinx.coroutines.launch

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(navController: NavController, article: Article) {

    val viewModel:NewsViewModel = viewModel()

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        Toolbar(icon = Screen.WebViewScreen.icon, navController = navController)

    },
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveArticle(article = article)
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Added To Favorites")
                    }

                },
                backgroundColor = Color.Red, contentColor = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    Icons.Default.Favorite, "", Modifier.size(28.dp)
                )
            }
        },

    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {
            

                AndroidView(factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        webViewClient = WebViewClient()
                        article.url?.let { loadUrl(it) }
                    }

                })


            
        }

    }


}


