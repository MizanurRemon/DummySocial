package com.example.dummysocial

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.dummysocial.BottomNavigation.BottomNavigationBar
import com.example.dummysocial.Model.Post.Data
import com.example.dummysocial.Navigation.NavigationDrawer
import com.example.dummysocial.Navigation.StartNavigation
import com.example.dummysocial.Network.NetworkStateViewModel
import com.example.dummysocial.Network.checkConnection
import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.Utils.ShowToast
import com.example.dummysocial.ViewModel.PostViewModel
import com.example.dummysocial.ViewModel.UserDetailsViewModel
import com.example.dummysocial.ui.theme.DummySocialTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val postViewModel: PostViewModel by viewModels()
    private val networkStateViewModel: NetworkStateViewModel by viewModels()

    var height: Int = 0
    var width: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // on below line we are creating and
        // initializing variable for display metrics.
        val displayMetrics = DisplayMetrics()

        // on below line we are getting metrics
        // for display using window manager.
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        // on below line we are getting height
        // and width using display metrics.
        height = displayMetrics.heightPixels
        width = displayMetrics.widthPixels

        checkNetwork()
        //(application as DaggerApplication).applicationComponent.inject(this)
        setContent {

            DummySocialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    MainUI()
                }
            }
        }
    }

    @Composable
    fun MainUI() {
        val navController = rememberNavController()
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()


        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(

                    backgroundColor = colorResource(R.color.default_color),
                    title = {
                        Text(
                            stringResource(id = R.string.app_name).toUpperCase(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            //ShowToast.successToast(context, "navigation")
                            scope.launch { scaffoldState.drawerState.open() }

                        }) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "menu",
                                tint = Color.White
                            )
                        }
                    },


                    actions = {
                        IconButton(onClick = {
                            context.startActivity(Intent(context, UsersListActivity::class.java))
                        }) {
                            Icon(
                                painterResource(id = R.drawable.messenger),
                                contentDescription = "messenger",
                                tint = Color.White
                            )
                        }
                    }
                )
            },
            drawerBackgroundColor = colorResource(id = R.color.default_color),
            drawerContent = {
                NavigationDrawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController
                )

                //Text(text = "Drawer")
            },
            bottomBar = {
                Column() {

                    BottomNavigationBar(navController)
                }
            }
        ) { innerPadding ->


            val userDetailsViewModel: UserDetailsViewModel by viewModels()
            Box(modifier = Modifier.padding(innerPadding)) {
                StartNavigation(
                    context,
                    navController,
                    postViewModel,
                    networkStateViewModel,
                    userDetailsViewModel
                )

            }


        }


    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        DummySocialTheme {
            MainUI()
        }
    }

    private fun checkNetwork() {
        lifecycleScope.launchWhenStarted {
            checkConnection().collect {
                if (it) {
                    //ShowToast.successToast(this@MainActivity, "Internet connected")
                    networkStateViewModel.changeNetWorkState(true)

                } else {
                    networkStateViewModel.changeNetWorkState(false)
                    ShowToast.errorToast(this@MainActivity, "Connection Error")

                }
            }
        }
    }

}




