package com.example.dummysocial.Model

import com.google.gson.annotations.SerializedName

data class User_response(
    @SerializedName("data"  ) var data  : ArrayList<Data> = arrayListOf(),
    @SerializedName("total" ) var total : Int?            = null,
    @SerializedName("page"  ) var page  : Int?            = null,
    @SerializedName("limit" ) var limit : Int?            = null


)

data class Data (

    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("title"     ) var title     : String? = null,
    @SerializedName("firstName" ) var firstName : String? = null,
    @SerializedName("lastName"  ) var lastName  : String? = null,
    @SerializedName("picture"   ) var picture   : String? = null

)
