package com.example.dummysocial.View

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.dummysocial.Model.PostDetails.PostDetails_response
import com.example.dummysocial.Network.checkConnection

import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.Utils.MyCircularProgress
import com.example.dummysocial.Utils.ScreenSize
import com.example.dummysocial.Utils.ShowToast
import com.example.dummysocial.ViewModel.PostDetailsViewModel
import com.example.dummysocial.ViewModel.PostViewModel
import com.example.dummysocial.ui.theme.DummySocialTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PostDetailsActivity : ComponentActivity() {

    private val postDetailsViewModel: PostDetailsViewModel by viewModels()

    var title: String = ""

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

        //checkNetwork()
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
    private fun MainUI() {
//        val navController = rememberNavController()
//        val context = LocalContext.current
//        val scope = rememberCoroutineScope()
//        val scaffoldState = rememberScaffoldState()

        Scaffold { innerPadding ->
            val id = intent.getStringExtra("id").toString()
//            postDetailsViewModel.getPostDetails(id)
////            postDetailsViewModel.response.observe(this, Observer {
////                Log.d("dataxx", "single data" + it.text)
////                //getData(response = it)
////
////                title = it.text
////
////            })
//
//            val result = postDetailsViewModel.response.value
//            if (result != null) {
//                Log.d("dataxx", "single data:: " + result.text)
//            }else{
//                Log.d("dataxx", "single data:: " )
//            }
//            getData(id)
            postDetailsViewModel.getPostDetails(id)
            when (val result = postDetailsViewModel.response.value) {
                is ApiState.SuccessPostDetails -> {
                    Log.d("dataxx", "getPostDetsils VM: ${result.data.text}")

                    getData(result.data)
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
    }

    @Composable
    private fun getData(response: PostDetails_response) {
        Column() {
            Image(
                painter = rememberImagePainter(response.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(ScreenSize.width().dp, 200.dp),

                )
        }
    }


    private fun checkNetwork() {
        lifecycleScope.launchWhenStarted {
            checkConnection().collect {
                if (it) {
                    ShowToast.successToast(this@PostDetailsActivity, "Internet connected")
                } else {
                    ShowToast.successToast(this@PostDetailsActivity, "Connection Error")
                }
            }
        }
    }
}