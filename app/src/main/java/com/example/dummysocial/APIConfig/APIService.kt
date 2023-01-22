package com.example.dummysocial.APIConfig

import com.example.dummysocial.Model.User_response
import retrofit2.http.GET

interface APIService {

    @GET("user")
    suspend fun getUsers():User_response
}