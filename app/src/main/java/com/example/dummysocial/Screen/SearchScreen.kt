package com.example.dummysocial.Screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.*


import com.example.dummysocial.R
import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.Utils.MyCircularProgress
import com.example.dummysocial.Utils.ShowToast
import com.example.dummysocial.ViewModel.TagViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun SearchScreen(context: Context, tagViewModel: TagViewModel) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.construction))
    val progress by animateLottieCompositionAsState(composition)

    val tagList: ArrayList<String> = arrayListOf()
    val searchList: ArrayList<String> = arrayListOf()


    when (val result = tagViewModel.response.value) {
        is ApiState.SuccessTags -> {


//            result.data.data.forEach(
//                tagList.add(result.data.data[it])
//            )

//            tagList.clear()
//            tagList.addAll(result.data.data)

            //Log.d("dataxx", "getTagslist: ${tagList.size.toString()}")

            for (i in 0 until 5){
                //Log.d("dataxx", "getTagslist: ${result.data.data[i].toString()}")
                tagList.add(result.data.data[i])
            }


            for (i in 0 until 5){
                Log.d("dataxx", "getTagslist: ${tagList[4].toString()}")
            }
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
            .padding(10.dp)
    ) {

        var text by remember {
            mutableStateOf("")
        }
        var onValueChange: ((String) -> Unit)? = null


        Card(modifier = Modifier.fillMaxWidth()) {
            ConstraintLayout() {

                val (searchButton, searchBox) = createRefs()

                TextField(
                    value = text,
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
                        text = it

                        Log.d("dataxx", "SearchScreen: ${tagList[0].toString()}")

                        /*var setList: MutableSet<String> = HashSet()
                        for (i in 0 until tagList.size) {
                            if (tagList[i].lowercase(Locale.ROOT)
                                    .contains(it.lowercase(Locale.ROOT))
                            ) {
                                setList.add(tagList[i])
                            }
                        }
                        searchList.clear()
                        searchList.addAll(setList)*/

                        //Log.d("dataxx", "SearchScreen: ${searchList.size.toString()}")
                        //setDataInToList(searchUserList)
                        //ShowToast.errorToast(context, text.toString())
                    })


                IconButton(
                    onClick = { /*TODO*/ },
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

    }
}


@Preview(showBackground = true)
@Composable
fun searchPreview() {

}