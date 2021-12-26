package com.example.e_times.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.material.icons.outlined.Article
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route:String, val icon:ImageVector, val label:String){
    object NewsScreen:Screen("news_screen", icon = Icons.Outlined.Article, "News")
    object FavoritesScreen:Screen("favorites_screen", icon = Icons.Default.Favorite, "Saved")
    object SearchScreen:Screen("search_screen", icon = Icons.Default.Search, "Search")
    object WebViewScreen:Screen("details_screen", icon = Icons.Default.ArrowBack, "")
}
