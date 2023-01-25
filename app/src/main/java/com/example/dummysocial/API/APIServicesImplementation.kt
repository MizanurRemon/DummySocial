package com.example.dummysocial.API

import com.example.dummysocial.Model.Post.Post_response
import com.example.dummysocial.Model.User.User_response
import javax.inject.Inject

class APIServicesImplementation @Inject
constructor(private val apiService: APIService) {

    suspend fun getUsers(limit: String, page: String): User_response =
        apiService.getUser(limit, page)

    suspend fun getPosts(limit: String, page: String): Post_response =
        apiService.getPosts(limit, page)
}