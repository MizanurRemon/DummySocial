package com.example.dummysocial.Navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dummysocial.Screen.HomeScreen
import com.example.dummysocial.Screen.SearchScreen
import com.example.dummysocial.ViewModel.PostViewModel

@Composable
fun StartNavigation(
    context: Context,
    navController: NavHostController,
    postViewModel: PostViewModel
) {
    //var navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationItem.HomeScreen.route) {
        composable(NavigationItem.HomeScreen.route) {
            HomeScreen(postViewModel)
        }

        composable(NavigationItem.SearchScreen.route) {
            SearchScreen(context)
        }
    }
}