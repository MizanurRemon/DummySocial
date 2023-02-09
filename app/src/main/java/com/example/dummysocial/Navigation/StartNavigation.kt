package com.example.dummysocial.Navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dummysocial.Network.NetworkStateViewModel
import com.example.dummysocial.Room.ViewModel.FavoritePostViewModel
import com.example.dummysocial.Screen.FavoriteScreen
import com.example.dummysocial.Screen.HomeScreen
import com.example.dummysocial.Screen.ProfileScreen
import com.example.dummysocial.Screen.SearchScreen
import com.example.dummysocial.ViewModel.*

@Composable
fun StartNavigation(
    context: Context,
    navController: NavHostController,
    postViewModel: PostViewModel,
    networkStateViewModel: NetworkStateViewModel,
    userDetailsViewModel: UserDetailsViewModel,
    userPostViewModel: UserPostViewModel,
    tagViewModel: TagViewModel,
    postByTagViewModel: PostByTagViewModel,
    favoritePostViewModel: FavoritePostViewModel
) {
    //var navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationItem.HomeScreen.route) {
        composable(NavigationItem.HomeScreen.route) {
            HomeScreen(postViewModel, networkStateViewModel, userDetailsViewModel, favoritePostViewModel)
        }

        composable(NavigationItem.SearchScreen.route) {
            SearchScreen(context, tagViewModel, postByTagViewModel, userDetailsViewModel, favoritePostViewModel)
        }


        composable(NavigationItem.ProfileScreen.route) {
            ProfileScreen(context,navController, userDetailsViewModel, userPostViewModel, favoritePostViewModel)
        }


        composable(NavigationItem.FavoriteScreen.route){
            FavoriteScreen(navController, favoritePostViewModel)
        }
    }
}


