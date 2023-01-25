package com.example.dummysocial.Model.Post

data class Post_response(
    val data: List<Data>,
    val limit: Int,
    val page: Int,
    val total: Int
)