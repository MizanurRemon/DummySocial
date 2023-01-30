package com.example.dummysocial.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dummysocial.Helpers.changeDateFormat
import com.example.dummysocial.Model.UserDetails.User_details_response
import com.example.dummysocial.R

@Composable
fun AboutScreen(response: User_details_response){
    Box(modifier = Modifier.padding(20.dp)) {
        Column() {
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
                    painterResource(id = R.drawable.gender),
                    modifier = Modifier.size(16.dp),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(5.dp))
                var address =
                    "${response.gender}"
                Text(text = address, style = TextStyle(fontSize = 12.sp))
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
                    "${response.location.street} ${response.location.city}, ${response.location.state}, ${response.location.country}"
                Text(text = address, style = TextStyle(fontSize = 12.sp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(id = R.drawable.call),
                    modifier = Modifier.size(16.dp),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(5.dp))
                var address =
                    "${response.phone}"
                Text(text = address, style = TextStyle(fontSize = 12.sp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(id = R.drawable.mail),
                    modifier = Modifier.size(16.dp),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(5.dp))
                var address =
                    "${response.email}"
                Text(text = address, style = TextStyle(fontSize = 12.sp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(id = R.drawable.birthday),
                    modifier = Modifier.size(16.dp),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(5.dp))
                var address =
                    changeDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                        "MMM d, yyyy", response.dateOfBirth
                    )
                Text(text = address, style = TextStyle(fontSize = 12.sp))
            }
        }
    }
}