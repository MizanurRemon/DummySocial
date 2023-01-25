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
import com.example.dummysocial.Navigation.StartNavigation
import com.example.dummysocial.Network.checkConnection
import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.Utils.ShowToast
import com.example.dummysocial.ViewModel.PostViewModel
import com.example.dummysocial.ui.theme.DummySocialTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val postViewModel: PostViewModel by viewModels()

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
        Scaffold(
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
                            //testClick("menu", context)
                            context.startActivity(Intent(context, NavigationActivity::class.java))

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
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) {
            //getData(userViewModel = userViewModel)
            //getPosts(postViewModel = postViewModel)
            StartNavigation(context, navController, postViewModel)
            //HomeScreen(navController = navController)
        }


    }

    @Composable
    fun getPosts(postViewModel: PostViewModel) {
        when (val result = postViewModel.response.value) {
            is ApiState.SuccessPost -> {
                Log.d("dataxx", "getPost: ${result.data.total.toString()}")
                LazyColumn() {
                    items(result.data.data) { response ->
                        //getAdapter(response)
                        getPostAdapter(response)
                    }
                }
            }

            is ApiState.Failure -> {
                Log.d("dataxx", "getData: ${result.msg.toString()}")
            }

            ApiState.Loading -> {
                MyCircularProgress()
            }

            ApiState.Empty -> {

            }
        }
    }

    @Composable
    private fun getPostAdapter(response: Data) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            elevation = 1.dp,
            shape = RoundedCornerShape(4.dp),
            //backgroundColor = colorResource(R.color.LightGrey),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(

                        painter = rememberImagePainter(response.owner.picture),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp, 30.dp)
                            .clip(CircleShape)                       // clip to the circle shape
                            .border(1.dp, Color.Gray, CircleShape)
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )

                    Column() {
                        Text(
                            text = "${response.owner.firstName} ${response.owner.lastName}",
                            fontSize = 14.sp,
                            //color = Color.Black
                        )

                        Text(
                            text = "${response.publishDate}",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                }

                Text(
                    text = "${response.text}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )

                Image(
                    painter = rememberImagePainter(response.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width.dp, 200.dp),

                    )

                Row() {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painterResource(R.drawable.favorite),
                            contentDescription = "react",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painterResource(R.drawable.share),
                            contentDescription = "share",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Text(
                    text = "${response.likes.toString()} likes",
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                )
            }
        }
    }

    @Composable
    fun MyCircularProgress() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(80.dp, 80.dp)
            )
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
                    ShowToast.successToast(this@MainActivity, "Internet connected")
                } else {
                    ShowToast.successToast(this@MainActivity, "Connection Error")
                }
            }
        }
    }

}




