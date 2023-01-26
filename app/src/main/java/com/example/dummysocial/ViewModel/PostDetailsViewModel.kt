package com.example.dummysocial.ViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.dummysocial.Model.PostDetails.PostDetails_response
import com.example.dummysocial.Repository.MainRepository
import com.example.dummysocial.Utils.ApiState
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostDetailsViewModel @Inject
constructor(private val mainRepository: MainRepository, savedStateHandle: SavedStateHandle) : ViewModel() {

    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)

    init {

        val id: String? = savedStateHandle["id"]
        Log.d("dataxx", "id: ${id.toString()}")
        getPostDetails(id.toString())
    }

    fun getPostDetails(id: String) = viewModelScope.launch {
        mainRepository.getPostDetails(id).onStart {
            response.value = ApiState.Loading
        }.catch {
            response.value = ApiState.Failure(it)
        }.collect {
            //Log.d("dataxx", "POST Details VM:: ${response.toString()}")
            response.value = ApiState.SuccessPostDetails(it)

        }
    }
    /*val response: MutableLiveData<PostDetails_response> = MutableLiveData()


    fun getPostDetails(id: String) {
        viewModelScope.launch {
            mainRepository.getPostDetails(id = id).onStart {

            }.catch { e ->
                Log.d(
                    "dataxx",
                    "ERROR ${e.message} "
                )
            }.collect {

                response.value = it
            }
        }
    }*/
}