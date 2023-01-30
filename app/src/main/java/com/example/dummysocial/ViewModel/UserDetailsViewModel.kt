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
class UserDetailsViewModel @Inject constructor(
    val mainRepository: MainRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)

    init {
        val id: String? = savedStateHandle["userid"]
        //val id = "60d0fe4f5311236168a109d1"
        Log.d("dataxx", "userid: ${id.toString()}")
        getUserDetails(id.toString())
    }

    private fun getUserDetails(id: String) = viewModelScope.launch {
        mainRepository.getUserDetails(id).onStart {
            response.value = ApiState.Loading
        }.catch {
            response.value = ApiState.Failure(it)
        }.collect {
            Log.d("dataxx", "USERSDETAILS VM:: ${response.toString()}")
            response.value = ApiState.SuccessUserDetails(it)
        }
    }
}