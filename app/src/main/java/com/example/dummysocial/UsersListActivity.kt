package com.example.dummysocial

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.dummysocial.Model.User.User_data_response
import com.example.dummysocial.Utils.ApiState
import com.example.dummysocial.ViewModel.UserViewModel
import com.example.dummysocial.ui.theme.DummySocialTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UsersListActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

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
        Scaffold(
            topBar = {
                TopAppBar(

                    backgroundColor = colorResource(R.color.default_color),
                    title = {
                        Text(
                            "Users".toUpperCase(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                )
            },
        ) {
            getData(userViewModel = userViewModel)
        }
    }

    @Composable
    fun getData(userViewModel: UserViewModel) {
        when (val result = userViewModel.response.value) {
            is ApiState.Success -> {
                Log.d("dataxx", "getData: ${result.data.total.toString()}")
                LazyColumn() {
                    items(result.data.data) { response ->
                        getAdapter(response)
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
    fun MyCircularProgress() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(80.dp, 80.dp)
            )
        }
    }

    @Composable
    fun getAdapter(response: User_data_response) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            elevation = 1.dp,
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(response.picture),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp)
                )
                Column(modifier = Modifier.padding(5.dp)) {
                    Text(
                        text = response.id.toString(), color = Color.Gray, fontSize = 10.sp,
                    )
                    Text(
                        text = response.title.toString() + " " + response.firstName.toString() + " " + response.lastName.toString(),
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
            }
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

