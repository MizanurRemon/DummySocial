package com.example.dummysocial.Room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dummysocial.Room.Model.FavoritePost
import com.example.dummysocial.Room.ViewModel.FavoritePostViewModel
import kotlinx.coroutines.flow.Flow


@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoritePost(postData: FavoritePost)

    @Query("SELECT count() as cnt from favoritePosts WHERE postid = :postID")
    fun checkPostAvailability(postID: String): Int

    @Query("select * from favoritePosts")
    fun getAllFavoritePost(): List<FavoritePost>

    @Query("DELETE from favoritePosts WHERE postid=:postID")
    fun deletePostFromFavorite(postID: String)


    @Query("SELECT status from favoritePosts WHERE postid = :postID")
    fun getStatusOfPost(postID: String): Int
}