package com.example.dummysocial.Screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.dummysocial.Helpers.isNightMode
import com.example.dummysocial.Model.UserDetails.User_details_response
import com.example.dummysocial.R

import com.example.dummysocial.Tab.TextTabs
import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.Utils.MyCircularProgress
import com.example.dummysocial.ViewModel.UserDetailsViewModel
import com.example.dummysocial.ViewModel.UserPostViewModel
import com.example.dummysocial.ui.theme.DummySocialTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@Composable
fun ProfileScreen(
    context: Context,
    userDetailsViewModel: UserDetailsViewModel,
    userPostViewModel: UserPostViewModel
) {
    DummySocialTheme {

        when (val result = userDetailsViewModel.response.value) {
            is ApiState.SuccessUserDetails -> {
                Log.d("dataxx", "getUser: ${result.data.toString()}")
                MainUI(result.data, userPostViewModel)
            }

            is ApiState.Failure -> {
                Log.d("dataxx", "Failure: ${result.msg.toString()}")
            }

            ApiState.Loading -> {
                MyCircularProgress()
            }

            ApiState.Empty -> {

            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainUI(response: User_details_response, userPostViewModel: UserPostViewModel) {

    val context = LocalContext.current
    val topCardColor = if (isNightMode(context)) R.color.night_card else R.color.yellow

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) {
        Card(
            backgroundColor = colorResource(id = topCardColor),
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()

        ) {

            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(response.picture.toString()),
                    modifier = Modifier
                        .size(80.dp, 80.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.purple_200)),
                    contentScale = ContentScale.Crop,
                    contentDescription = "profileImage",
                    alignment = Alignment.Center

                )

                Spacer(Modifier.weight(1f))


                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Hi, Its")

                    Text(
                        text = "${response.firstName.toString()}",
                        style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    )
                }

            }
        }

        val pagerState = rememberPagerState(2)

        val list = listOf(
            "Dashboard",
            "About"
        )


        TextTabs(pagerState = pagerState, list)

        TabsContent(pagerState = pagerState, response, userPostViewModel)
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
    response: User_details_response,
    userPostViewModel: UserPostViewModel
) {
    // on below line we are creating
    // horizontal pager for our tab layout.
    HorizontalPager(
        state = pagerState, modifier =
        Modifier.fillMaxSize()
    ) { page ->
        TabContentScreen(page, response, userPostViewModel)
    }
}

@Composable
fun TabContentScreen(
    page: Int,
    response: User_details_response,
    userPostViewModel: UserPostViewModel
) {

    Column(
        // in this column we are specifying modifier
        // and aligning it center of the screen on below lines.
        modifier = Modifier.fillMaxSize(),

        ) {
        // in this column we are specifying the text
        when (page) {
            0 -> when (val result = userPostViewModel.response.value) {
                is ApiState.SuccessPost -> {

                    PostScreen(data = result.data.data)
                }

                is ApiState.Failure -> {
                    Log.d("dataxx", "Failure: ${result.msg.toString()}")
                }

                ApiState.Loading -> {
                    MyCircularProgress()
                }

                ApiState.Empty -> {

                }
            }
            1 -> AboutScreen(response = response)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun profilePreview() {
    DummySocialTheme {
        //MainUI(result.data)
    }
}