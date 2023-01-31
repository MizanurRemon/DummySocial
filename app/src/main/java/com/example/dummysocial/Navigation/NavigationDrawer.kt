package com.example.dummysocial.Navigation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dummysocial.Helpers.isNightMode
import com.example.dummysocial.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController
) {
    val items = listOf(
        NavigationItem.HomeScreen,
        NavigationItem.SearchScreen
    )

    val context: Context = LocalContext.current

    val bgColor =
        if (isNightMode(context = context)) colorResource(id = R.color.night_card) else colorResource(id = R.color.white)

    Column(
        modifier = Modifier
            .background(bgColor)
    ) {

        drawerHeader(context)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->

            NavigationDrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                navController.navigate(item.route) {

                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    // Avoid multiple copies of the same destination when
                    // re-selecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                // Close drawer
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })

        }
        Spacer(modifier = Modifier.weight(1f))

    }
}

@Composable
fun drawerHeader(context: Context) {

    val context = LocalContext.current
    val headerColor =
        if (isNightMode(context = context)) colorResource(id = R.color.default_color) else colorResource(id = R.color.DarkOrange)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(headerColor)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .padding(top = 20.dp, bottom = 40.dp)
                .size(100.dp)
                .clip(RoundedCornerShape(5)),
            contentScale = ContentScale.Crop

        )
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerPreview() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val navController = rememberNavController()
    NavigationDrawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
}