package com.example.dummysocial

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.dummysocial.Navigation.StartNavigation
import com.example.dummysocial.ui.theme.DummySocialTheme

class NavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DummySocialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainUI()
                }
            }
        }
    }

    @Composable
    fun MainUI() {

        val context = LocalContext.current
        Scaffold(
            topBar = {
                TopAppBar(

                    backgroundColor = colorResource(R.color.default_color),
                    title = {
                        Text(
                            "Navigation".toUpperCase(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    },

                )
            },
        ) {
            StartNavigation(this)
        }


    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        DummySocialTheme {
           MainUI()
        }
    }
}

