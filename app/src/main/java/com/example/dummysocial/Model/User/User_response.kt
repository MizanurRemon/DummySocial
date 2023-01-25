package com.example.dummysocial.Model.User

import com.google.gson.annotations.SerializedName

data class User_response(
    @SerializedName("data") var data: ArrayList<User_data_response> = arrayListOf(),
    @SerializedName("total") var total: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("limit") var limit: Int? = null


)


