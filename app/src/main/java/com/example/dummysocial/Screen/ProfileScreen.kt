package com.example.dummysocial.Screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.dummysocial.Helpers.ListState
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
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    context: Context,
    navController: NavHostController,
    userDetailsViewModel: UserDetailsViewModel,
    userPostViewModel: UserPostViewModel
) {
    DummySocialTheme {


        when (val result = userDetailsViewModel.response.value) {
            is ApiState.SuccessUserDetails -> {
                Log.d("dataxx", "getUser: ${result.data.toString()}")
                MainUI(result.data, userPostViewModel, navController, userDetailsViewModel)
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
fun MainUI(
    response: User_details_response,
    userPostViewModel: UserPostViewModel,
    navController: NavHostController,
    userDetailsViewModel: UserDetailsViewModel
) {

    val context = LocalContext.current
    val topCardColor = if (isNightMode(context)) R.color.night_card else R.color.yellow

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)

    ) {

//        IconButton(onClick = {
//            navController.popBackStack()
//        }) {
//            Icon(
//                Icons.Default.ArrowBack,
//                contentDescription = "react",
//                modifier = Modifier.size(24.dp)
//            )
//        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = topCardColor))
        ) {

            val (profileImage, topText, bottomText) = createRefs()
            Image(
                painter = rememberImagePainter(response.picture.toString()),
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.purple_200))
                    .constrainAs(profileImage) {
                        start.linkTo(parent.start, margin = 10.dp)
                        top.linkTo(parent.top, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                    },
                contentScale = ContentScale.Crop,
                contentDescription = "profileImage",
                alignment = Alignment.Center

            )


            Text(text = "Hi, Its", modifier = Modifier.constrainAs(topText) {
                end.linkTo(parent.end, margin = 10.dp)
                top.linkTo(parent.top, margin = 10.dp)
                bottom.linkTo(bottomText.top, margin = 5.dp)

            })

            Text(text = "${response.firstName.toString()}",
                style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.constrainAs(bottomText) {
                    top.linkTo(topText.bottom, margin = 5.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                })
        }
//        Card(
//            backgroundColor = colorResource(id = topCardColor),
//            elevation = 0.dp,
//            shape = RoundedCornerShape(10.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//
//        ) {
//
//
//
//
//
//        }

        val pagerState = rememberPagerState(2)

        val list = listOf(
            "Dashboard", "About"
        )

        Box(
            modifier = Modifier.padding(
                start = 10.dp,
                end = 10.dp
            )
        ) {
            TextTabs(pagerState = pagerState, list)
        }
        TabsContent(pagerState = pagerState, response, userPostViewModel, userDetailsViewModel)
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
    response: User_details_response,
    userPostViewModel: UserPostViewModel,
    userDetailsViewModel: UserDetailsViewModel
) {
    // on below line we are creating
    // horizontal pager for our tab layout.
    HorizontalPager(
        state = pagerState, modifier = Modifier.fillMaxSize()
    ) { page ->
        TabContentScreen(page, response, userPostViewModel, userDetailsViewModel)
    }
}

@Composable
fun TabContentScreen(
    page: Int,
    response: User_details_response,
    userPostViewModel: UserPostViewModel,
    userDetailsViewModel: UserDetailsViewModel,
) {

    Column(
        // in this column we are specifying modifier
        // and aligning it center of the screen on below lines.
        modifier = Modifier.fillMaxSize(),

        ) {
        // in this column we are specifying the text
        when (page) {
            0 -> DashboardScreen(userPostViewModel, userDetailsViewModel)
            1 -> AboutScreen(response = response)
        }
    }

}


@Composable
private fun DashboardScreen(
    userPostViewModel: UserPostViewModel,
    userDetailsViewModel: UserDetailsViewModel
) {
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val shouldStartPaginate = remember {
        derivedStateOf {
            userPostViewModel.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }

    val postList = userPostViewModel.postList

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && userPostViewModel.listState == ListState.IDLE) userPostViewModel.getUserPosts()
    }

    LazyColumn(state = lazyColumnListState) {
        items(
            items = postList,
            //key = { it.url },
        ) { post ->
            PostAdapter(response = post, userDetailsViewModel)
        }

        item(
            key = userPostViewModel.listState,
        ) {
            when (userPostViewModel.listState) {
                ListState.LOADING -> {
                    Column(
                        modifier = Modifier.fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp), text = "Loading", fontSize = 10.sp
                        )

                        CircularProgressIndicator(
                            color = Color.Black, modifier = Modifier.size(20.dp), strokeWidth = 1.dp
                        )
                    }
                }
                ListState.PAGINATING -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp), text = "Refreshing", fontSize = 10.sp
                        )
                        CircularProgressIndicator(
                            color = Color.Black, modifier = Modifier.size(20.dp), strokeWidth = 1.dp
                        )
                    }
                }
                ListState.PAGINATION_EXHAUST -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp, vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(imageVector = Icons.Rounded.Face, contentDescription = "")

                        Text(text = "Nothing left.")

                        TextButton(modifier = Modifier.padding(top = 8.dp),
                            elevation = ButtonDefaults.elevation(0.dp),
                            onClick = {
                                coroutineScope.launch {
                                    lazyColumnListState.animateScrollToItem(0)
                                }
                            },
                            content = {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowUp,
                                        contentDescription = ""
                                    )

                                    Text(text = "Back to Top")

                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowUp,
                                        contentDescription = ""
                                    )
                                }
                            })
                    }
                }
                else -> {}
            }
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