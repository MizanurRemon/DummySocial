package com.example.dummysocial.Screen

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.dummysocial.Helpers.changeDateFormat
import com.example.dummysocial.Helpers.sendImageAsFile
import com.example.dummysocial.Model.Post.Data
import com.example.dummysocial.Navigation.ACtivityNavigation.navigateToUserDetailsActivity
import com.example.dummysocial.R
import com.example.dummysocial.Utils.ScreenSize
import com.example.dummysocial.Utils.ShowToast
import com.example.dummysocial.View.PostDetailsActivity

@Composable
fun PostScreen(data: List<Data>) {

    val context = LocalContext.current
    val scrollState = rememberLazyListState()

    var page = 0

    LazyColumn(state = scrollState) {
        items(data) { response ->
            //getAdapter(response)
            getPostAdapter(response)
        }

    }

    val endOfListReached by remember {
        derivedStateOf {
            scrollState.isScrolledToEnd()
        }
    }

    // act when end of list reached
    LaunchedEffect(endOfListReached) {
        // do your stuff
        //ShowToast.successToast(context, "end")
        page++

        Log.d("dataxx", "PostScreen: ${page.toString()}")
    }
}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1


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
                        .fillMaxSize()
                        .clickable {
                            navigateToUserDetailsActivity(response.owner.id, context)
                        },
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )

                Column() {
                    Text(
                        text = "${response.owner.firstName} ${response.owner.lastName}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navigateToUserDetailsActivity(response.owner.id, context)
                        }
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painterResource(id = R.drawable.favorite),
                        contentDescription = "react",
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(onClick = { sendImageAsFile(context, response.image) }) {
                    Icon(
                        painterResource(R.drawable.share),
                        contentDescription = "share",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(Modifier.weight(1f))

                Text(
                    text = "${response.likes.toString()} likes",
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                )
            }


        }
    }
}