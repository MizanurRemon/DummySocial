package com.example.dummysocial.View

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.dummysocial.Helpers.changeDateFormat
import com.example.dummysocial.Helpers.isNightMode
import com.example.dummysocial.Model.PostComment.Data
import com.example.dummysocial.Model.PostComment.PostComment_response
import com.example.dummysocial.Model.PostDetails.PostDetails_response
import com.example.dummysocial.Network.checkConnection
import com.example.dummysocial.R
import com.example.dummysocial.Utils.*

import com.example.dummysocial.ViewModel.PostCommentsViewModel
import com.example.dummysocial.ViewModel.PostDetailsViewModel
import com.example.dummysocial.ViewModel.PostViewModel
import com.example.dummysocial.ui.theme.DummySocialTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log


@AndroidEntryPoint
class PostDetailsActivity : ComponentActivity() {

    private val postDetailsViewModel: PostDetailsViewModel by viewModels()

    private val postCommentsViewModel: PostCommentsViewModel by viewModels()

    var title: String = ""

    var height: Int = 0
    var width: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // on below line we are creating and
        // initializing variable for display metrics.
        val displayMetrics = DisplayMetrics()

        // on below line we are getting metrics
        // for display using window manager.
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        // on below line we are getting height
        // and width using display metrics.
        height = displayMetrics.heightPixels
        width = displayMetrics.widthPixels

        //checkNetwork()
        //(application as DaggerApplication).applicationComponent.inject(this)
        setContent {


            DummySocialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {

                    MainUI()

                }
            }
        }
    }

    @Composable
    private fun MainUI() {
//        val navController = rememberNavController()
//        val context = LocalContext.current
//        val scope = rememberCoroutineScope()
//        val scaffoldState = rememberScaffoldState()

        Scaffold {

            Column() {
                loadPostDetails()

                loadComment()


            }
        }
    }

    @Composable
    private fun loadPostDetails() {
        when (val result = postDetailsViewModel.response.value) {
            is ApiState.SuccessPostDetails -> {
                Log.d("dataxx", "getPostDetsils ACT: ${result.data.text}")

                getData(result.data)
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
    private fun getData(response: PostDetails_response) {
        var dialogState by remember {
            mutableStateOf(false)
        }

        var imageUrl by remember {
            mutableStateOf("")
        }

        largeImageDialog(dialogState = dialogState, imageUrl = imageUrl, onDismissRequest = {
            dialogState = !it
        })

        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Row(
                modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 5.dp),
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
                                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MMM d, yyyy", response.publishDate
                            )
                        }", fontSize = 10.sp, color = Color.Gray
                    )
                }
            }

            Text(
                text = "${response.text}",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )

            Box(modifier = Modifier.size(ScreenSize.width().dp, 200.dp)) {
                Image(
                    painter = rememberImagePainter(response.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )

                IconButton(
                    onClick = {
                        dialogState = true
                        imageUrl = response.image.toString()
                    },
                    modifier = Modifier.align(Alignment.BottomEnd),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_fullscreen_24),
                        tint = colorResource(id = R.color.white),
                        contentDescription = "fullscreen"
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
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


                Spacer(Modifier.weight(1f))

                Text(
                    text = "${response.likes.toString()} likes",
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                )
            }


            Text(
                text = "Comments",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(10.dp)
            )


        }
    }


    @Composable
    private fun loadComment() {
        when (val result = postCommentsViewModel.response.value) {
            is ApiState.SuccessPostComment -> {
                Log.d("dataxx", "getPostComment ACT: ${result.data.data.size.toString()}")

                LazyColumn() {
                    items(result.data.data) { response ->
                        //getAdapter(response)
                        getCommentAdapter(response)
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
    private fun getCommentAdapter(response: Data) {


        val cardColor =
            if (isNightMode(context = LocalContext.current)) R.color.status_color else R.color.Azure

        Row(
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            //verticalAlignment = Alignment.CenterVertically
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

            Card(
                //modifier = Modifier.fillMaxWidth(),
                elevation = 0.dp, backgroundColor = colorResource(
                    id = cardColor
                )
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "${response.owner.firstName} ${response.owner.lastName}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                        //color = Color.Black
                    )

                    Spacer(modifier = Modifier.padding(2.dp))

                    Text(
                        text = response.message + ". ",
                        fontSize = 12.sp,
                    )
                }
            }
        }


    }

    @Composable
    private fun largeImageDialog(
        dialogState: Boolean, imageUrl: String, onDismissRequest: (dialogState: Boolean) -> Unit
    ) {
        var scale = remember { mutableStateOf(1f) }
        val rotationState = remember { mutableStateOf(1f) }

        if (dialogState) {
            AlertDialog(title = null,
                text = null,
                onDismissRequest = { onDismissRequest(dialogState) },
                properties = DialogProperties(
                    dismissOnBackPress = true, dismissOnClickOutside = true
                ),
                buttons = {
                    Box(modifier = Modifier.pointerInput(Unit) {
                        detectTransformGestures { centroid, pan, zoom, rotation ->
                            scale.value *= zoom
                            rotationState.value += rotation
                        }
                    }) {
                        Image(
                            rememberImagePainter(imageUrl),
                            contentDescription = "imageZoom",
                            modifier = Modifier
                                .width(ScreenSize.width().dp)
                                .height((ScreenSize.height() / 2).dp)
                                .graphicsLayer(
                                    // adding some zoom limits (min 50%, max 200%)
                                    scaleX = maxOf(.5f, minOf(3f, scale.value)),
                                    scaleY = maxOf(.5f, minOf(3f, scale.value)),
                                    //rotationZ = rotationState.value
                                ),
                            contentScale = ContentScale.Crop,

                            //    modifier = Modifier.fillMaxSize()
                        )
                        TextButton(
                            onClick = {
                                onDismissRequest(dialogState)
                                //lscale = mutableStateOf(1f)
                            },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Text(text = "CLOSE", color = colorResource(id = R.color.DarkOrange))
                        }
                    }
                }

            )
        }
    }


    private fun checkNetwork() {
        lifecycleScope.launchWhenStarted {
            checkConnection().collect {
                if (it) {
                    ShowToast.successToast(this@PostDetailsActivity, "Internet connected")
                } else {
                    ShowToast.successToast(this@PostDetailsActivity, "Connection Error")
                }
            }
        }
    }
}


