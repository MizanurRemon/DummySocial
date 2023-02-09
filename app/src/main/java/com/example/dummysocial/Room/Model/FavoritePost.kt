package com.example.dummysocial.Room.Model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favoritePosts")
data class FavoritePost(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,


    @ColumnInfo(name = "postid")
    var postid: String,

    @ColumnInfo(name = "text")
    var text: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "likes")
    var likes: String,

    @ColumnInfo(name = "ownerid")
    var ownerid: String,

    @ColumnInfo(name = "firstname")
    var firstname: String,

    @ColumnInfo(name = "lastname")
    var lastname: String,

    @ColumnInfo(name = "ownerimage")
    var ownerimage: String,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "date")
    var date: String,
)
