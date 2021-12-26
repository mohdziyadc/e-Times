package com.example.e_times.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.e_times.BottomBar
import com.example.e_times.NewsCard
import com.example.e_times.NewsViewModel
import com.example.e_times.Toolbar
import com.example.e_times.models.Article
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun FavoritesScreen(navController:NavController){

    val viewModel: NewsViewModel = viewModel()

    viewModel.getAllFavorites()

    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Toolbar(Icons.Default.Menu, navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController )
        },
        scaffoldState = state,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(viewModel.favoritesList.value, {article:Article -> article.id}){ article ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if(it == DismissValue.DismissedToEnd){
                               viewModel.deleteArticle(article)

                                scope.launch {
                                    val snackBarResult = state.snackbarHostState.showSnackbar(
                                        message = "Deleted From Favorites",
                                        actionLabel = "UNDO"
                                    )

                                    when(snackBarResult){
                                        SnackbarResult.Dismissed -> return@launch
                                        SnackbarResult.ActionPerformed -> viewModel.saveArticle(article)
                                    }
                                }

                            }
                            true
                        }
                    )
                    SwipeToDismiss(state = dismissState, background = {
                        val color = when(dismissState.dismissDirection){
                            DismissDirection.StartToEnd -> Color.Red
                            DismissDirection.EndToStart -> Color.Transparent
                            null -> Color.Transparent
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 16.dp, end = 16.dp, top = 8.dp)

                        ){
                            Card(
                                modifier = Modifier.fillMaxSize(),
                                backgroundColor = color

                            ){
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "", tint = Color.White
                                    ,modifier = Modifier
                                        .wrapContentWidth(align = Alignment.Start)
                                        .padding(start = 16.dp)
                                )
                            }
                        }
                    },
                    dismissContent = {
                        NewsCard(news = article, navController = navController)
                    },
                        directions = setOf(DismissDirection.StartToEnd)
                    )



                }
            }
        }


    }
}

/*
In the lazy column, the key should be mentioned in items() so as to get the position of the
current card accurately.
 */