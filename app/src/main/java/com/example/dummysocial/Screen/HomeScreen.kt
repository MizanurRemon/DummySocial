package com.example.dummysocial.Screen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.dummysocial.Navigation.NavigationScreen
import com.example.dummysocial.ViewModel.PostViewModel

@Composable
fun HomeScreen(navController: NavHostController) {

    val postViewModel:PostViewModel = viewModel()
    Button(onClick = {
        navController.navigate(NavigationScreen.SearchScreen.route)
    }) {
        Text(text = "Click Here")
    }
}