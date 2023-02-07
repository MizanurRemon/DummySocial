package com.example.dummysocial.ViewModel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummysocial.Helpers.ListState
import com.example.dummysocial.Model.Post.Data
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

    val postList = mutableStateListOf<Data>()
    private var page by mutableStateOf(0)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(ListState.IDLE)

    init {
        getPosts()
    }


    fun getPosts() = viewModelScope.launch {
        if (page == 0 || (page != 0 && canPaginate) && listState == ListState.IDLE) {
            listState = if (page == 0) ListState.LOADING else ListState.PAGINATING

            mainRepository.getPosts("10", page.toString()).collect() {
                if (it.data.isNotEmpty()) {
                    canPaginate = it.data.size == 10

                    if (page == 0) {
                        postList.clear()
                        postList.addAll(it.data)
                    } else {
                        postList.addAll(it.data)
                    }

                    listState = ListState.IDLE

                    if (canPaginate)
                        page++
                } else {
                    listState = if (page == 0) ListState.ERROR else ListState.PAGINATION_EXHAUST
                }
            }
        }
    }

    override fun onCleared() {
        page = 0
        listState = ListState.IDLE
        canPaginate = false
        super.onCleared()
    }

}