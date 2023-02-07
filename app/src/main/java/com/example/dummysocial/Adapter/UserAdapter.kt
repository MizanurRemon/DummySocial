package com.example.dummysocial.Adapter

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.dummysocial.Model.User.User_data_response
import com.example.dummysocial.View.UserDetailsActivity

@Composable
fun UserAdapter(context: Context, response: User_data_response) {
    val context: Context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                //ShowToast(context, "kj")
//                    Toast
//                        .makeText(
//                            context,
//                            "You have tapped on ${response.firstName.toString()}",
//                            Toast.LENGTH_SHORT
//                        )
//                        .show()

                val intent = Intent(context, UserDetailsActivity::class.java)
                intent.putExtra("userid", response.id)
                context.startActivity(intent)

            },
        elevation = 1.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberImagePainter(response.picture),
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .size(60.dp)
                    .clip(RoundedCornerShape(50.dp))
            )
            Column(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = response.id.toString(), color = Color.Gray, fontSize = 10.sp,
                )
                Text(
                    text = response.firstName.toString() + " " + response.lastName.toString(),
                    fontSize = 14.sp,
                )
            }
        }
    }
}