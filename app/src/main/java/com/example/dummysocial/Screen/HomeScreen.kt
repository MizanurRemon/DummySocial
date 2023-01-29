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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.dummysocial.Helpers.changeDateFormat
import com.example.dummysocial.Model.Post.Data
import com.example.dummysocial.Navigation.NavigationItem
import com.example.dummysocial.Network.NetworkStateViewModel
import com.example.dummysocial.R
import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.Utils.MyCircularProgress
import com.example.dummysocial.Utils.ScreenSize
import com.example.dummysocial.Utils.ShowToast
import com.example.dummysocial.View.PostDetailsActivity
import com.example.dummysocial.ViewModel.PostViewModel
import kotlinx.coroutines.flow.collect
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
            /*Log.d(
                "dataxx",
                "getPostDate: ${result.data.data[0].publishDate.toString()} to ${
                    changeDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                        "MMM d, yyyy",
                        "2020-05-24T14:53:17.598Z"
                    )
                }"
            )*/

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
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                var intent = Intent(Intent(context, PostDetailsActivity::class.java))
                intent.putExtra("id", response.id)
                context.startActivity(intent)
            },
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
                        text = " ${
                            changeDateFormat(
                                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                "MMM d, yyyy",
                                response.publishDate
                            )
                        }",
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
                    .size(ScreenSize.width().dp, 200.dp),

                )

            Row() {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painterResource(id = R.drawable.favorite),
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

