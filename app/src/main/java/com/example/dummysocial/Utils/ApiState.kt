package com.example.dummysocial.Utils

import com.example.dummysocial.Model.Post.Post_response
import com.example.dummysocial.Model.PostDetails.PostDetails_response
import com.example.dummysocial.Model.User.User_response

sealed class ApiState {
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()
    object Empty : ApiState()

    class Success(val data: User_response) : ApiState()

    class SuccessPost(val data: Post_response) : ApiState()

    class SuccessPostDetails(val data: PostDetails_response) : ApiState()

}
