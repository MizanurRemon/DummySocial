package com.example.dummysocial.PagingDataSource

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.dummysocial.Model.User.User_data_response

import com.example.dummysocial.Model.User.User_response
import com.example.dummysocial.Model.UserDetails.User_details_response
import com.example.dummysocial.Repository.MainRepository
import com.example.dummysocial.Utils.ApiState
import javax.inject.Inject

//class UsersDataSource(private val mainRepository: MainRepository) :
//    PagingSource<Int, User_data_response>() {
//    override fun getRefreshKey(state: PagingState<Int, User_data_response>): Int? {
//        return state.anchorPosition?.let {
//            val page = state.closestPageToPosition(it)
//            page?.prevKey?.minus(-1) ?: page?.nextKey?.plus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User_data_response> {
//
//        return try {
//
//            val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)
//
//            val page = params.key ?: 1  //if the key is null it will return 1
//            mainRepository.getUser("10", page.toString()).collect{user->
//                response.value = it.data
//            }
//
//
//            LoadResult.Page(
//                data = response.,
//                prevKey = null,
//                nextKey =,
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//            //Log.d("dataxx", "Pagination Users: ${e.message}")
//        }
//
//    }
//
//}