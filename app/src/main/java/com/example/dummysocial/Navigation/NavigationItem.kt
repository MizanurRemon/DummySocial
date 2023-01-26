package com.example.dummysocial.Navigation

import com.example.dummysocial.R

sealed class NavigationItem(val route: String, var icon: Int, val title: String) {

    object HomeScreen : NavigationItem("homeScreen", R.drawable.home, "Home")
    object SearchScreen :
        NavigationItem("searchScreen", R.drawable.search, "Search")
}
