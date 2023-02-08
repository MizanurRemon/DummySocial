package com.example.dummysocial.Screen

import android.content.Intent
import android.util.Log
import android.view.Gravity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import coil.compose.rememberImagePainter
import com.example.dummysocial.Helpers.changeDateFormat
import com.example.dummysocial.Helpers.isNightMode
import com.example.dummysocial.Helpers.sendImageAsFile
import com.example.dummysocial.Model.Post.Data
import com.example.dummysocial.Navigation.ACtivityNavigation.navigateToUserDetailsActivity
import com.example.dummysocial.R
import com.example.dummysocial.Utils.ScreenSize
import com.example.dummysocial.View.PostDetailsActivity
import com.example.dummysocial.ViewModel.UserDetailsViewModel


@Composable
fun PostAdapter(response: Data, userDetailsViewModel: UserDetailsViewModel) {
    val context = LocalContext.current


    var dialogState by remember {
        mutableStateOf(false)
    }

    var id by remember {
        mutableStateOf("")
    }

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
                            //navigateToUserDetailsActivity(response.owner.id, context)
                            dialogState = true
                            id = response.owner.id
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
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
            }


        }
    }

    BottomSheetDialog(id, userDetailsViewModel, dialogState, onDismissRequest = {
        dialogState = !it
    })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomSheetDialog(
    id: String,
    userDetailsViewModel: UserDetailsViewModel,
    dialogState: Boolean,
    onDismissRequest: (dialogState: Boolean) -> Unit
) {

    var dialogHeight by remember {
        mutableStateOf(200.0)
    }

    val bgColor = if (isNightMode(LocalContext.current)) R.color.default_color else R.color.white
    val textColor = if (isNightMode(LocalContext.current)) R.color.white else R.color.black

    if (dialogState) {

        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = {
                onDismissRequest(dialogState)
                dialogHeight = 200.0
            },
        ) {

            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            dialogWindowProvider.window.setGravity(Gravity.BOTTOM)

            Surface(
                color = colorResource(
                    id = bgColor
                ),
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .height(dialogHeight.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consumeAllChanges()
//                            if (dialogHeight <= 200 && dialogHeight >= 800) {
//
//                            }

                            dialogHeight = (dialogHeight - dragAmount.y).coerceIn(200.0, 400.0)
                            Log.d("dataxx", "value: ${dragAmount.y.dp.value.toString()}}")


                            //.coerceIn(0.0, (size.height.toFloat() + 50.dp.toPx()).toDouble())
                        }
                    }

            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {

                    Row() {
                        Button(onClick = {
                            dialogHeight += 10
                        }) {
                            Text(text = "INC")
                        }

                        Spacer(modifier = Modifier.size(5.dp))

                        Button(onClick = {
                            if (dialogHeight > 200) {
                                dialogHeight -= 10
                            }
                        }) {
                            Text(text = "DEC")
                        }
                    }
                    Text(text = dialogHeight.toString())


                }
                //Text(text = id)
//                userDetailsViewModel.getUserDetails(id)
//                when (val result = userDetailsViewModel.response.value) {
//                    is ApiState.SuccessUserDetails -> {
//                        Log.d("dataxx", "BottomSheetDialog data: ${result.data.toString()}")
//                        Text(text = result.data.firstName)
//                    }
//
//                    is ApiState.Failure -> {
//                        Log.d("dataxx", "Failure: ${result.msg.toString()}")
//                    }
//
//                    ApiState.Loading -> {
//                        MyCircularProgress()
//                    }
//
//                    ApiState.Empty -> {
//
//                    }
//                }
            }
        }
    }
}

fun limit(value: Int): Int {
    return Math.max(0, Math.min(value, 200))
}