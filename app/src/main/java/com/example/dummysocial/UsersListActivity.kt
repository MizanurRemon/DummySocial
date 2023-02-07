package com.example.dummysocial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dummysocial.Adapter.UserAdapter
import com.example.dummysocial.Helpers.ListState
import com.example.dummysocial.ViewModel.UserViewModel
import com.example.dummysocial.ui.theme.DummySocialTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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
        val lazyColumnListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        val shouldStartPaginate = remember {
            derivedStateOf {
                userViewModel.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
            }
        }

        val userList = userViewModel.userList

        LaunchedEffect(key1 = shouldStartPaginate.value) {
            if (shouldStartPaginate.value && userViewModel.listState == ListState.IDLE)
                userViewModel.getUser()
        }

        LazyColumn(state = lazyColumnListState) {
            items(
                items = userList,
            ) { user ->
                UserAdapter(LocalContext.current, response = user)
            }

            item(
                key = userViewModel.listState,
            ) {
                when (userViewModel.listState) {
                    ListState.LOADING -> {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = "Loading", fontSize = 10.sp
                            )

                            CircularProgressIndicator(
                                color = Color.Black,
                                modifier = Modifier.size(20.dp).padding(5.dp),
                                strokeWidth = 1.dp
                            )
                        }
                    }
                    ListState.PAGINATING -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = "Refreshing", fontSize = 10.sp
                            )
                            CircularProgressIndicator(
                                color = Color.Black,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 1.dp
                            )
                        }
                    }
                    ListState.PAGINATION_EXHAUST -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp, vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Icon(imageVector = Icons.Rounded.Face, contentDescription = "")

                            Text(text = "Nothing left.")

                            TextButton(
                                modifier = Modifier
                                    .padding(top = 8.dp),
                                elevation = ButtonDefaults.elevation(0.dp),
                                onClick = {
                                    coroutineScope.launch {
                                        lazyColumnListState.animateScrollToItem(0)
                                    }
                                },
                                content = {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.KeyboardArrowUp,
                                            contentDescription = ""
                                        )

                                        Text(text = "Back to Top")

                                        Icon(
                                            imageVector = Icons.Rounded.KeyboardArrowUp,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            )
                        }
                    }
                    else -> {}
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

