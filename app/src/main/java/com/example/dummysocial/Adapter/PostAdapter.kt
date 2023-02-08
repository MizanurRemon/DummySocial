package com.example.dummysocial.Screen

import android.content.Intent
import android.util.Log
import android.view.Gravity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.TextStyle
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
import com.example.dummysocial.Model.Post.Owner
import com.example.dummysocial.Navigation.ACtivityNavigation.navigateToUserDetailsActivity
import com.example.dummysocial.R
import com.example.dummysocial.Utils.ScreenSize
import com.example.dummysocial.View.PostDetailsActivity
import com.example.dummysocial.ViewModel.UserDetailsViewModel
import kotlinx.coroutines.NonDisposableHandle.parent


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
//                            navigateToUserDetailsActivity(response.owner.id, context)
                            dialogState = true
                            id = response.owner.id
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

    BottomSheetDialog(response.owner, userDetailsViewModel, dialogState) {
        dialogState = !it
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomSheetDialog(
    owner: Owner,
    userDetailsViewModel: UserDetailsViewModel,
    dialogState: Boolean,
    onDismissRequest: (dialogState: Boolean) -> Unit
) {

    val context = LocalContext.current
    var dialogHeight by remember {
        mutableStateOf(200)
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
                dialogHeight = 200
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

                            dialogHeight = (dialogHeight - dragAmount.y.toInt()).coerceIn(200, 400)
                            Log.d("dataxx", "value: ${dragAmount.y.dp.value.toString()}}")


                            //.coerceIn(0.0, (size.height.toFloat() + 50.dp.toPx()).toDouble())
                        }
                    }

            ) {


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {

                    Image(
                        painter = rememberImagePainter(owner.picture.toString()),
                        modifier = Modifier
                            .size(80.dp, 80.dp)
                            .clip(CircleShape)                       // clip to the circle shape
                            .fillMaxSize()
                            .background(color = colorResource(id = R.color.purple_200)),
                        contentScale = ContentScale.Crop,
                        contentDescription = "profileImage",
                        alignment = Alignment.Center

                    )

                    Divider(color = colorResource(id = R.color.transparent_color))
                    Text(text = owner.firstName)
                    Divider(color = colorResource(id = R.color.transparent_color))
                    Text(text = owner.lastName)

                    Divider(color = colorResource(id = R.color.transparent_color))

                    Button(onClick = {
                        onDismissRequest(dialogState)
                        navigateToUserDetailsActivity(
                            owner.id,
                            context = context
                        )
                    }) {
                        Text(
                            text = "Full Details",
                            style = TextStyle(
                                colorResource(id = R.color.white),
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }


            }
        }
    }
}

fun limit(value: Int): Int {
    return Math.max(0, Math.min(value, 200))
}