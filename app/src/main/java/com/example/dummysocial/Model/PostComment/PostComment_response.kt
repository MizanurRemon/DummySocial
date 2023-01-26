package com.example.dummysocial.Model.PostComment

data class PostComment_response(
    val `data`: List<Data>,
    val limit: Int,
    val page: Int,
    val total: Int
)