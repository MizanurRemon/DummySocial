package com.example.dummysocial.Screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.dummysocial.Navigation.StartNavigation


import com.example.dummysocial.R
import com.example.dummysocial.UsersListActivity
import com.example.dummysocial.ui.theme.DummySocialTheme

@Composable
fun SearchScreen(context: Context) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.construction))
    val progress by animateLottieCompositionAsState(composition)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .padding(10.dp)
            .size(200.dp)) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
            )

        }

    }
}


@Preview(showBackground = true)
@Composable
fun searchPreview() {
    DummySocialTheme {
        SearchScreen(LocalContext.current)
    }
}