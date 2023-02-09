package com.example.dummysocial.Screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.*
import com.example.dummysocial.Helpers.ListState


import com.example.dummysocial.R
import com.example.dummysocial.Room.ViewModel.FavoritePostViewModel
import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.Utils.MyCircularProgress
import com.example.dummysocial.Utils.ScreenSize
import com.example.dummysocial.ViewModel.PostByTagViewModel
import com.example.dummysocial.ViewModel.TagViewModel
import com.example.dummysocial.ViewModel.UserDetailsViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

@SuppressLint(
    "CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition",
    "MutableCollectionMutableState"
)
@Composable
fun SearchScreen(
    context: Context,
    tagViewModel: TagViewModel,
    postByTagViewModel: PostByTagViewModel,
    userDetailsViewModel: UserDetailsViewModel,
    favoritePostViewModel: FavoritePostViewModel
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.construction))
    val progress by animateLottieCompositionAsState(composition)

    val tagList: ArrayList<String> = arrayListOf()
    var searchList: MutableState<ArrayList<String>> = remember {
        mutableStateOf(ArrayList())
    }

    var dropDownWidth by remember { mutableStateOf(0) }

    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }


    when (val result = tagViewModel.response.value) {
        is ApiState.SuccessTags -> {

            tagList.clear()
            tagList.addAll(result.data.data)

        }
        is ApiState.Failure -> {
            Log.d("dataxx", "getTags: ${result.msg.toString()}")
        }

        ApiState.Loading -> {
            MyCircularProgress()
            Log.d("dataxx", "LOADING")
        }

        ApiState.Empty -> {

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        var searchText by remember {
            mutableStateOf("")
        }
        var onValueChange: ((String) -> Unit)? = null


        Box(modifier = Modifier.padding(10.dp)) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    dropDownWidth = it.width
                }) {
                ConstraintLayout() {

                    val (searchButton, searchBox) = createRefs()

                    TextField(
                        value = searchText,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        modifier = Modifier
                            .constrainAs(
                                searchBox
                            ) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(searchButton.start)
                            },
                        placeholder = { Text(text = "Search") },
                        onValueChange = {
                            searchText = it

                            if (searchText.isNotEmpty()) {
                                var setList: MutableSet<String> = HashSet()
                                for (i in 0 until tagList.size) {
                                    try {
                                        if (tagList[i].lowercase(Locale.ROOT)
                                                .contains(it.lowercase(Locale.ROOT))
                                        ) {
                                            setList.add(tagList[i])

                                        }
                                    } catch (e: Exception) {

                                    }
                                }
                                searchList.value.clear()
                                searchList.value.addAll(setList)
                                expanded = searchList.value.size < 20

                                //Log.d("dataxx", "SearchScreen: ${searchList.value.size.toString()}")
                            } else {
                                expanded = false
                                searchList.value.clear()
                            }
                            //setDataInToList(searchUserList)
                            //ShowToast.errorToast(context, text.toString())
                        })


                    IconButton(
                        onClick = {
                            //ShowToast.successToast(context, searchText)
                            postByTagViewModel.getPostByTag(searchText.trim())
                            //searchData(postByTagViewModel, searchText.trim().toString())
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .constrainAs(searchButton) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(searchBox.end)
                            }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = ""
                        )
                    }
                }
            }

            DropdownMenu(
                modifier = Modifier
                    .size(
                        dropDownWidth.dp, (ScreenSize.height() / 2.5).dp
                    )
                    .background(
                        Color.Transparent
                    ),
                expanded = expanded,
                properties = PopupProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    focusable = false
                ),
                onDismissRequest = { expanded = false },

                ) {
                searchList.value.forEach() { s ->
                    DropdownMenuItem(onClick = {
                        searchText = s
                        expanded = false
                    }) {

                        Column() {
                            Text(text = s)
                            Divider(thickness = (1 / 2).dp)
                        }
                    }
                }
            }


        }

        searchData(
            postByTagViewModel = postByTagViewModel,
            userDetailsViewModel,
            favoritePostViewModel,
            searchText = searchText.trim().toString()
        )


    }
}


@Composable
fun searchData(
    postByTagViewModel: PostByTagViewModel,
    userDetailsViewModel: UserDetailsViewModel,
    favoritePostViewModel: FavoritePostViewModel,
    searchText: String
) {
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val shouldStartPaginate = remember {
        derivedStateOf {
            postByTagViewModel.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }

    val postList = postByTagViewModel.postList

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && postByTagViewModel.listState == ListState.IDLE)
            postByTagViewModel.getPostByTag(searchText)
//            if (searchText.isEmpty()) {
//                ShowToast.errorToast(context = context, "empty")
//            } else {
//                postByTagViewModel.getPostByTag(searchText)
//            }
    }

    LazyColumn(state = lazyColumnListState) {
        items(
            items = postList,
        ) { post ->
            PostAdapter(response = post, userDetailsViewModel, favoritePostViewModel)
        }

        item(
            key = postByTagViewModel.listState,
        ) {
            when (postByTagViewModel.listState) {
                ListState.LOADING -> {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Loading", fontSize = 10.sp
                        )

                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier
                                .size(20.dp)
                                .padding(5.dp),
                            strokeWidth = 1.dp
                        )
                    }
                }
                ListState.PAGINATING -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refreshing", fontSize = 10.sp
                        )
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 1.dp
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

                        TextButton(
                            modifier = Modifier
                                .padding(top = 8.dp),
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
                            }
                        )
                    }
                }
                else -> {}
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun searchPreview() {

}