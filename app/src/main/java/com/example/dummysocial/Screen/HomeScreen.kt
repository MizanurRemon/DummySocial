package com.example.dummysocial.Screen

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.dummysocial.Helpers.changeDateFormat
import com.example.dummysocial.Model.Post.Data
import com.example.dummysocial.Network.NetworkStateViewModel
import com.example.dummysocial.R
import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.Utils.MyCircularProgress
import com.example.dummysocial.Utils.ScreenSize
import com.example.dummysocial.View.PostDetailsActivity
import com.example.dummysocial.ViewModel.PostViewModel
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(postViewModel: PostViewModel, networkStateViewModel: NetworkStateViewModel) {
    getPosts(postViewModel = postViewModel, networkStateViewModel)
    var scope = rememberCoroutineScope()
    scope.launch {
        networkStateViewModel.networkState.collect {



        }
    }

    val networkState by remember {
        mutableStateOf(networkStateViewModel.networkState)

    }


}


@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun getPosts(postViewModel: PostViewModel, networkStateViewModel: NetworkStateViewModel) {


    when (val result = postViewModel.response.value) {
        is ApiState.SuccessPost -> {
            Log.d("dataxx", "getPost: ${result.data.total.toString()}")


            PostScreen(result.data.data)

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



