package com.example.dummysocial.Room.Repository

import com.example.dummysocial.Room.Dao.PostDao
import com.example.dummysocial.Room.Model.FavoritePost
import com.example.dummysocial.Room.ViewModel.FavoritePostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDBRepository @Inject
constructor(private val dao: PostDao) {

    suspend fun addFavoritePost(favoritePost: FavoritePost) = withContext(Dispatchers.IO) {
        dao.addFavoritePost(favoritePost)
    }

    suspend fun checkPostAvailability(postID: String) = withContext(Dispatchers.IO) {
        dao.checkPostAvailability(postID)
    }

    suspend fun getAllFavoritePost(): List<FavoritePost> = withContext(Dispatchers.IO) {
        dao.getAllFavoritePost()
    }


    suspend fun deletePostFromFavorite(postID: String) = withContext(Dispatchers.IO) {
        dao.deletePostFromFavorite(postID)
    }


     fun getStatusOfPost(postID: String): Int{
        return dao.getStatusOfPost(postID)
    }


}