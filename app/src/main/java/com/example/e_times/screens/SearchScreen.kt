package com.example.e_times.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.e_times.BottomBar
import com.example.e_times.NewsCard
import com.example.e_times.NewsViewModel
import com.example.e_times.Toolbar
import com.example.e_times.Utils.Constants.Companion.BASE_URL
import com.example.e_times.Utils.Constants.Companion.PAGE_SIZE
import com.example.e_times.models.Article
import com.example.e_times.network.pagination.SearchNewsSource
import com.example.e_times.ui.theme.LightBlack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalComposeUiApi
@Composable
fun SearchScreen(navController: NavController){
    val viewModel: NewsViewModel = viewModel()
    val newsList = viewModel.searchNewsList.value.data?.articles
    val page = viewModel.searchNewsPage.value
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()


//    Log.v("SearchScreen", "NewsList: $searchNewsList" )

    val loading = viewModel.searchResponseState.value
    val error = viewModel.exceptionOccurred.value
    Scaffold(
        topBar = {
            Toolbar(Icons.Default.Menu, navController)

        },
        bottomBar = {
            BottomBar(navController = navController )
        },
        scaffoldState = scaffoldState
    ) {innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(viewModel = viewModel)
            Box(modifier = Modifier.padding(innerPadding)){
                if(!error){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ){

                        newsList?.let {
                            itemsIndexed(it){ index,article ->
                                viewModel.checkScrollPosition(index)
                                if((index +1) >= (page*PAGE_SIZE)){
                                    viewModel.nextPage()

                                }
                                NewsCard(news = article, navController = navController)

                                if(loading){
                                    Column(modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center) {

                                        CircularProgressIndicator(
                                            color = MaterialTheme.colors.LightBlack
                                        )
                                    }
                                }





                            }
                        }


                    }
                } else{
                    LaunchedEffect(key1 = "BASE"){
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please check your internet connection", duration = SnackbarDuration.Long)
                        }

                    }

                }



            }









        }






    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchBar(viewModel: NewsViewModel){

    val keyboardController = LocalSoftwareKeyboardController.current  //to hide the keyboard on done
    val searchText = viewModel.searchText
    Log.v("SearchScreen", "SearchText: ${viewModel.searchText.value}" )


    TextField(value = searchText.value, onValueChange = {
        searchText.value = it
        resetState(viewModel = viewModel)

    },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        placeholder = {
            Text(text = "Search...", color = Color.White)
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = MaterialTheme.colors.LightBlack,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            placeholderColor = Color.White.copy(0.4f)
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions =  KeyboardActions(onSearch = {
            keyboardController?.hide()
            viewModel.getSearchNews()

        }),
        trailingIcon = {
            if (searchText.value.isNotEmpty()) {
                IconButton(
                    onClick = {
                        searchText.value= "" // Remove text from TextField when you press the 'X' icon
                        keyboardController?.hide()
                        resetState(viewModel)


                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }

        }
    )
}

fun resetState(viewModel: NewsViewModel){
    viewModel.searchNewsPage.value = 1
    viewModel.getSearchNews()
    viewModel.checkScrollPosition(0)
}

