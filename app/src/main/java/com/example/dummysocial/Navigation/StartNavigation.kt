package com.example.dummysocial.Navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dummysocial.Network.NetworkStateViewModel
import com.example.dummysocial.Screen.HomeScreen
import com.example.dummysocial.Screen.ProfileScreen
import com.example.dummysocial.Screen.SearchScreen
import com.example.dummysocial.ViewModel.PostViewModel
import com.example.dummysocial.ViewModel.UserDetailsViewModel
import com.example.dummysocial.ViewModel.UserPostViewModel

@Composable
fun StartNavigation(
    context: Context,
    navController: NavHostController,
    postViewModel: PostViewModel,
    networkStateViewModel: NetworkStateViewModel,
    userDetailsViewModel: UserDetailsViewModel,
    userPostViewModel: UserPostViewModel
) {
    //var navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationItem.HomeScreen.route) {
        composable(NavigationItem.HomeScreen.route) {
            HomeScreen(postViewModel, networkStateViewModel)
        }

        composable(NavigationItem.SearchScreen.route) {
            SearchScreen(context)
        }


        composable(NavigationItem.ProfileScreen.route) {
            ProfileScreen(context, userDetailsViewModel, userPostViewModel)
        }
    }
}