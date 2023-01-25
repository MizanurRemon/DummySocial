package com.example.dummysocial.BottomNavigation

import android.graphics.drawable.Icon
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.dummysocial.Navigation.NavigationScreen
import com.example.dummysocial.R

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        NavigationScreen.HomeScreen,
        NavigationScreen.SearchScreen
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.default_color),
        contentColor = Color.White
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    /* Add code later */
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}