package com.example.dummysocial.Screen

import android.content.Context
import android.content.Intent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.dummysocial.UsersListActivity

@Composable
fun SearchScreen(context: Context) {
    Button(onClick = {
        context.startActivity(Intent(context, UsersListActivity::class.java))
    }) {
        Text(text = "Go To USERS")
    }
}