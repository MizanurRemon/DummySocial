package com.example.dummysocial.Screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.*


import com.example.dummysocial.R
import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.Utils.MyCircularProgress
import com.example.dummysocial.Utils.ScreenSize
import com.example.dummysocial.Utils.ShowToast
import com.example.dummysocial.ViewModel.PostByTagViewModel
import com.example.dummysocial.ViewModel.TagViewModel
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
    postByTagViewModel: PostByTagViewModel
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

                                Log.d("dataxx", "SearchScreen: ${searchList.value.size.toString()}")
                            } else {
                                expanded = false
                                searchList.value.clear()
                            }
                            //setDataInToList(searchUserList)
                            //ShowToast.errorToast(context, text.toString())
                        })


                    IconButton(
                        onClick = {
                            ShowToast.successToast(context, searchText)
                            postByTagViewModel.getPostByTag(searchText.trim())
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

        when (val result = postByTagViewModel.response.value) {
            is ApiState.SuccessPost -> {

                PostScreen(result.data.data)

            }

            is ApiState.Failure -> {
                Log.d("dataxx", "SEARCH FAILURE: ${result.msg.toString()}")
            }

            ApiState.Loading -> {
                MyCircularProgress()
            }

            ApiState.Empty -> {

            }
        }


    }
}


@Preview(showBackground = true)
@Composable
fun searchPreview() {

}