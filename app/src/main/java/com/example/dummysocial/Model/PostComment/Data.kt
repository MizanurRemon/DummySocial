package com.example.dummysocial.Model.PostComment

data class Data(
    val id: String,
    val message: String,
    val owner: Owner,
    val post: String,
    val publishDate: String
)