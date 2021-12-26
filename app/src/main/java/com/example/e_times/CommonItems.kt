package com.example.e_times

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberImagePainter
import com.example.e_times.models.Article
import com.example.e_times.screens.Screen
import com.google.gson.Gson
import java.lang.StringBuilder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun Toolbar(icon:ImageVector, navController: NavController) {

    TopAppBar(
        title = {
            Text(
                text = "eTimes",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                if(icon != Icons.Default.Menu){
                    navController.popBackStack()
                }

            }) {
                Icon(
                    imageVector = icon ,
                    contentDescription = "Menu Icon",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp)
                )
            }


        }
    )


}

@Composable
fun BottomBar(navController: NavController) {

    val bottomBarItems = listOf(
        Screen.NewsScreen,
        Screen.FavoritesScreen,
        Screen.SearchScreen
    )

    BottomNavigation(
        contentColor = Color.White
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        bottomBarItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "Icon",
                        modifier = Modifier.size(28.dp)

                    )
                },
                alwaysShowLabel = false,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true

                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true


                    }

                },
                label = {
                    Text(text = item.label, modifier = Modifier.padding(16.dp))
                }
            )

        }
    }
}


@Composable
fun NewsCard(news:Article, navController: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .clickable {
                val articleToJson = Gson().toJson(news)
                //Need to encode URL otherwise will show Navigation path not found in graph (IllegalArgumentException)
                val encode = URLEncoder.encode(articleToJson, StandardCharsets.UTF_8.toString())
                val formattedArticle = encode.replace("+"," ")
                navController.navigate(Screen.WebViewScreen.route + "/$formattedArticle")
                Log.v("Card", "Article: $formattedArticle")
            }

               ,
        elevation = 4.dp
    ) {
        Row() {
            Image(
                painter = rememberImagePainter(
                    data = news.urlToImage
                ),
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
                    .padding(top = 16.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
                contentScale = ContentScale.FillHeight
            )

            Column {
                news.title?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                start = 0.dp,
                                end = 8.dp
                            )
                            .fillMaxWidth(),
                        maxLines = 2 ,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp

                    )
                }

                news.content?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(
                                top = 0.dp,
                                bottom = 16.dp,
                                start = 0.dp,
                                end = 8.dp
                            )
                            .fillMaxWidth(),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp,
                        letterSpacing = (0.3).sp

                    )
                }

            }
        }
    }

}

//fun urlCheck(news:Article, navController: NavController) {
//
//    if(news.url[4] =='s'){
//        val encodedUrl = URLEncoder.encode(news.url, StandardCharsets.UTF_8.toString())
//        navController.navigate(Screen.WebViewScreen.route + "/$encodedUrl")
//    } else {
//        val sb = StringBuilder(news.url)
//        sb.insert(4,'s')
//        val encodedUrl = URLEncoder.encode(sb.toString(), StandardCharsets.UTF_8.toString())
//        navController.navigate(Screen.WebViewScreen.route + "/$encodedUrl")
//    }
//}
