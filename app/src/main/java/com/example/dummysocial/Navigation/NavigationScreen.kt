package com.example.dummysocial.Navigation

import android.icu.text.CaseMap.Title
import com.example.dummysocial.R

sealed class NavigationScreen(val route: String, var icon: Int, val title: String) {

    object HomeScreen : NavigationScreen("homeScreen", R.drawable.ic_baseline_home_24, "Home")
    object SearchScreen :
        NavigationScreen("searchScreen", R.drawable.ic_baseline_search_24, "Search")
}
