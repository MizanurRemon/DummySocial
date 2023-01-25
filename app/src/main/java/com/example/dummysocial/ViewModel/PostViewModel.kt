package com.example.dummysocial.ViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummysocial.Repository.MainRepository
import com.example.dummysocial.Utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)

    init {
        getPosts("50", "0")
    }

    fun getPosts(limit: String, page: String) = viewModelScope.launch {
        mainRepository.getPosts(limit, page).onStart {
            response.value = ApiState.Loading
        }.catch {
            response.value = ApiState.Failure(it)
        }.collect {
            Log.d("dataxx", "POSTS VM:: ${response.toString()}")
            response.value = ApiState.SuccessPost(it)
        }
    }
}