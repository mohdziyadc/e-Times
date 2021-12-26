package com.example.e_times.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.e_times.*
import com.example.e_times.Utils.Resource
import com.example.e_times.models.News
import com.example.e_times.ui.theme.LightBlack
import kotlinx.coroutines.launch

@Composable
fun NewsScreen(navController: NavController){

    val viewModel:NewsViewModel = viewModel()
    val newsList = viewModel.article.collectAsLazyPagingItems()
    val error = viewModel.exceptionOccurred.value
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Log.v("NewsScreen", "Breaking News List: $newsList")
//    val responseState = viewModel.responseState


    Scaffold(
        topBar = {
            Toolbar(Icons.Default.Menu, navController =navController)
        },
        bottomBar = {
            BottomBar(navController = navController )
        }
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
//            viewModel.getBreakingNews(countryCode = "in")
            if(!error){
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    items(newsList){news ->
                        news?.let {
                            NewsCard(news = it, navController = navController)
                            Log.v("NewsScreen", "Breaking News : $news")
                        }

                    }

                    newsList.apply {
                        when{
                            loadState.refresh is LoadState.Loading -> item {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = MaterialTheme.colors.LightBlack
                                    )
                                }
                            }
                            loadState.append is LoadState.Loading ->{
                                item {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {

                                        CircularProgressIndicator(
                                            color = MaterialTheme.colors.LightBlack
                                        )
                                    }

                                }

                            }
                            loadState.append is LoadState.Error ->{
                                item {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LaunchedEffect(key1 = "FIRST"){
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please check your internet connection", duration = SnackbarDuration.Long)
                        }

                    }
                }

            }






        }

    }
}