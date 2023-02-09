package com.example.dummysocial.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import com.example.dummysocial.R

sealed class NavigationItem(val route: String, var icon: Int, val title: String) {

    object HomeScreen : NavigationItem("homeScreen", R.drawable.home, "Home")
    object SearchScreen :
        NavigationItem("searchScreen", R.drawable.search, "Search")

    object ProfileScreen :
        NavigationItem("profileScreen", R.drawable.user, "Profile")

    object FavoriteScreen: NavigationItem("testScreen", R.drawable.favoritefilled, "Favorite")
}
