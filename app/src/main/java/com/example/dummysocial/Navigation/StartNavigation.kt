package com.example.dummysocial.Navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dummysocial.Screen.HomeScreen
import com.example.dummysocial.Screen.SearchScreen

@Composable
fun StartNavigation(context: Context) {
    var navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationScreen.HomeScreen.route) {
        composable(NavigationScreen.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(NavigationScreen.SearchScreen.route) {
            SearchScreen(context)
        }
    }
}