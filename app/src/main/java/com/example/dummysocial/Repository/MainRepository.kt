package com.example.dummysocial.Repository

import android.util.Log
import com.example.dummysocial.API.APIServicesImplementation
import com.example.dummysocial.Model.Post.Post_response
import com.example.dummysocial.Model.User.User_response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject
constructor(private val apiServicesImplementation: APIServicesImplementation) {

    fun getUser(limit: String, page: String): Flow<User_response> = flow {
        val response = apiServicesImplementation.getUsers(limit, page)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getPosts(limit: String, page: String): Flow<Post_response> = flow {
        val response = apiServicesImplementation.getPosts(limit, page)
        Log.d("dataxx", "POSTS:: ${response.toString()}")
        emit(response)
    }.flowOn(Dispatchers.IO)
}