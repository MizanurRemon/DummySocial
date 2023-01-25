package com.example.dummysocial.API

import com.example.dummysocial.Model.Post.Post_response
import com.example.dummysocial.Model.User.User_response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService {

    @Headers("app-id:63cd111ff805cea1c4e8e6a3")
    @GET("user")
    suspend fun getUser(
        @Query("limit") limit: String,
        @Query("page") page: String
    ): User_response


    @Headers("app-id:63cd111ff805cea1c4e8e6a3")
    @GET("post")
    suspend fun getPosts(
        @Query("limit") limit: String,
        @Query("page") page: String
    ): Post_response
}

