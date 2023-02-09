package com.example.dummysocial.Room.ViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummysocial.Room.Model.FavoritePost
import com.example.dummysocial.Room.Repository.LocalDBRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritePostViewModel @Inject constructor(private val localDBRepository: LocalDBRepository) :
    ViewModel() {

    private var _checkPost: MutableStateFlow<Int> = MutableStateFlow(0)

    var cnt = 0

    var allFavoritePost: MutableState<List<FavoritePost>> = mutableStateOf(listOf())

    fun addFavoritePost(favoritePost: FavoritePost) = viewModelScope.launch {
        localDBRepository.addFavoritePost(favoritePost)
    }


    fun checkPostAvailability(postID: String) = viewModelScope.launch {

        _checkPost.value = localDBRepository.checkPostAvailability(postID)
        cnt = _checkPost.value


        Log.d("dataxx", "addFavoritePostData: ${postID} ${_checkPost.value.toString()}")
    }


    fun getAllFavoritePost() = viewModelScope.launch {
        allFavoritePost.value = localDBRepository.getAllFavoritePost()
        Log.d("dataxx", "getAllFavoritePost:  ${allFavoritePost.value.toString()}")
    }


    fun deletePostFromFavorite(postID: String) = viewModelScope.launch {
        localDBRepository.deletePostFromFavorite(postID)

        getAllFavoritePost()
    }

    fun getStatusOfPost(postID: String): Int {
        return localDBRepository.getStatusOfPost(postID)
    }


}