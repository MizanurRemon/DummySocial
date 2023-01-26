package com.example.dummysocial.ViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
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
class PostCommentsViewModel @Inject
constructor(private val mainRepository: MainRepository, savedStateHandle: SavedStateHandle) :
    ViewModel() {

    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)

    init {

        val id: String? = savedStateHandle["id"]
        Log.d("dataxx", "id: ${id.toString()}")
        getPostComment(id.toString())
    }

    private fun getPostComment(id: String) = viewModelScope.launch {
        mainRepository.getPostComment(id).onStart {
            response.value = ApiState.Loading
        }.catch {
            response.value = ApiState.Failure(it)
        }.collect {
            //Log.d("dataxx", "POST Details VM:: ${response.toString()}")
            response.value = ApiState.SuccessPostComment(it)

        }
    }

}