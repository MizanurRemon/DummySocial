package com.example.dummysocial.API

import com.example.dummysocial.Model.Post.Post_response
import com.example.dummysocial.Model.PostComment.PostComment_response
import com.example.dummysocial.Model.PostDetails.PostDetails_response
import com.example.dummysocial.Model.User.User_response
import com.example.dummysocial.Model.UserDetails.User_details_response
import javax.inject.Inject

class APIServicesImplementation @Inject
constructor(private val apiService: APIService) {

    suspend fun getUsers(limit: String, page: String): User_response =
        apiService.getUser(limit, page)

    suspend fun getUserDetails(id: String): User_details_response =
        apiService.getUserDetails(id)

    suspend fun getPosts(limit: String, page: String): Post_response =
        apiService.getPosts(limit, page)

    suspend fun getUserPosts(id: String): Post_response =
        apiService.getUserPosts(id)

    suspend fun getPostDetails(id: String): PostDetails_response =
        apiService.getPostDetails(id)

    suspend fun getPostComment(id: String): PostComment_response =
        apiService.getPostComment(id)
}