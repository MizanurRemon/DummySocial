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
class UserViewModel @Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

    /*val response: LiveData<User_response> = userRepository.getUser().catch { e ->
        Log.d(
            "dataxx",
            "ERROR ${e.message} "
        )
    }.asLiveData()*/

    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)

    init {
        getUser("50", "0")
    }

    fun getUser(limit: String, page: String) = viewModelScope.launch {
        mainRepository.getUser(limit, page).onStart {
            response.value = ApiState.Loading
        }.catch {
            response.value = ApiState.Failure(it)
        }.collect {
            Log.d("dataxx", "USERS VM:: ${response.toString()}")
            response.value = ApiState.Success(it)
        }
    }


}