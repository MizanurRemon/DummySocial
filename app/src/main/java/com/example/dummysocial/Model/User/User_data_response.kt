package com.example.dummysocial.Model.User

import com.google.gson.annotations.SerializedName

data class User_data_response(

    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("firstName") var firstName: String? = null,
    @SerializedName("lastName") var lastName: String? = null,
    @SerializedName("picture") var picture: String? = null

)