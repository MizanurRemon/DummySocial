package com.example.dummysocial.Screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.dummysocial.Model.Post.Data
import com.example.dummysocial.Navigation.NavigationItem
import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.Utils.ScreenSize
import com.example.dummysocial.ViewModel.PostViewModel

@Composable
fun HomeScreen(postViewModel: PostViewModel) {
    getPosts(postViewModel = postViewModel)
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
                    .size(ScreenSize.width().dp, 200.dp),

                )

            /* Row() {
                 IconButton(onClick = { /*TODO*/ }) {
                     Icon(
                         ,
                         contentDescription = "react",
                         modifier = Modifier.size(24.dp)
                     )
                 }

                 IconButton(onClick = { /*TODO*/ }) {
                     Icon(
                         painterResource(),
                         contentDescription = "share",
                         modifier = Modifier.size(24.dp)
                     )
                 }
             }*/

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