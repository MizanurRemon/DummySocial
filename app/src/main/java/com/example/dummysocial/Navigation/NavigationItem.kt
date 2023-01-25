package com.example.dummysocial.Navigation

import com.example.dummysocial.R

sealed class NavigationItem(val route: String, var icon: Int, val title: String) {

    object HomeScreen : NavigationItem("homeScreen", R.drawable.ic_baseline_home_24, "Home")
    object SearchScreen :
        NavigationItem("searchScreen", R.drawable.ic_baseline_search_24, "Search")
}
