package com.example.dummysocial.Screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.dummysocial.Model.UserDetails.User_details_response
import com.example.dummysocial.R
import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.Utils.MyCircularProgress
import com.example.dummysocial.ViewModel.UserDetailsViewModel
import com.example.dummysocial.ui.theme.DummySocialTheme

@Composable
fun ProfileScreen(context: Context, userDetailsViewModel: UserDetailsViewModel) {
    DummySocialTheme {

        when (val result = userDetailsViewModel.response.value) {
            is ApiState.SuccessUserDetails -> {
                Log.d("dataxx", "getUser: ${result.data.toString()}")
                MainUI(result.data)
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

@Composable
fun MainUI(response: User_details_response) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Card(
                backgroundColor = colorResource(id = R.color.yellow),
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



            Box(modifier = Modifier.padding(20.dp)) {
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(id = R.drawable.userfill),
                            modifier = Modifier.size(16.dp),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = response.firstName + " " + response.lastName)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(id = R.drawable.location),
                            modifier = Modifier.size(16.dp),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        var address =
                            "${response.location.street} ${response.location.city} ${response.location.state} ${response.location.country} "
                        Text(text = address, style = TextStyle(fontSize = 12.sp))
                    }
                }
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