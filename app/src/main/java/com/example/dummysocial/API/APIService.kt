package com.example.dummysocial.API

import com.example.dummysocial.Model.Post.Post_response
import com.example.dummysocial.Model.PostComment.PostComment_response
import com.example.dummysocial.Model.PostDetails.PostDetails_response
import com.example.dummysocial.Model.Tags.Tag_response
import com.example.dummysocial.Model.User.User_response
import com.example.dummysocial.Model.UserDetails.User_details_response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @Headers("app-id:63cd111ff805cea1c4e8e6a3")
    @GET("user")
    suspend fun getUser(
        @Query("limit") limit: String,
        @Query("page") page: String
    ): User_response


    @Headers("app-id:63cd111ff805cea1c4e8e6a3")
    @GET("user/{id}/")
    suspend fun getUserDetails(
        @Path("id") id: String
    ): User_details_response


    @Headers("app-id:63cd111ff805cea1c4e8e6a3")
    @GET("post")
    suspend fun getPosts(
        @Query("limit") limit: String,
        @Query("page") page: String
    ): Post_response

    @Headers("app-id:63cd111ff805cea1c4e8e6a3")
    @GET("post/{id}/")
    suspend fun getPostDetails(
        @Path("id") id: String
    ): PostDetails_response


    @Headers("app-id:63cd111ff805cea1c4e8e6a3")
    @GET("post/{id}/comment")
    suspend fun getPostComment(
        @Path("id") id: String
    ): PostComment_response


    @Headers("app-id:63cd111ff805cea1c4e8e6a3")
    @GET("user/{id}/post/")
    suspend fun getUserPosts(
        @Path("id") id: String,
        @Query("limit") limit: String,
        @Query("page") page: String
    ): Post_response


    @Headers("app-id:63cd111ff805cea1c4e8e6a3")
    @GET("tag/")
    suspend fun getTags(
    ): Tag_response

    @Headers("app-id:63cd111ff805cea1c4e8e6a3")
    @GET("tag/{tag}/post/")
    suspend fun getPostByTag(
        @Path("tag") tag: String,
        @Query("limit") limit: String,
        @Query("page") page: String
    ): Post_response
}

