package com.example.dummysocial.PagingDataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

import com.example.dummysocial.Model.User.User_response
import com.example.dummysocial.Model.UserDetails.User_details_response
import com.example.dummysocial.Repository.MainRepository
import javax.inject.Inject

//class UsersDataSource @Inject constructor(private val mainRepository: MainRepository) :
//    PagingSource<Int, User_response>() {
//    override fun getRefreshKey(state: PagingState<Int, User_response>): Int? {
//        //current page number
//        return state.anchorPosition?.let { pos ->
//            val page = state.closestPageToPosition(pos)
//            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User_response> {
//        return try {
//            val page = params.key ?: 1
//            val response = mainRepository.getUser("10", page.toString())
//
//            LoadResult.Page(
//                data = response,
//                prevKey = null,
//                nextKey = response
//
//            )
//
//        } catch (e: Exception) {
//            Log.d("dataxx", "load: ${e.message}")
//            return LoadResult.Error(e)
//        }
//    }
//
//
//}