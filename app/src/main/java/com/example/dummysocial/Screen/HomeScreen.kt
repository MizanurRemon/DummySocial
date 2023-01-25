package com.example.dummysocial.Screen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.dummysocial.Navigation.NavigationScreen

@Composable
fun HomeScreen(navController: NavHostController) {

    Button(onClick = {
        navController.navigate(NavigationScreen.SearchScreen.route)
    }) {
        Text(text = "Click Here")
    }
}