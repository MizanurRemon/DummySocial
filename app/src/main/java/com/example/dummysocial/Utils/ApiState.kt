package com.example.dummysocial.Utils

import com.example.dummysocial.Model.Post.Post_response
import com.example.dummysocial.Model.PostComment.PostComment_response
import com.example.dummysocial.Model.PostDetails.PostDetails_response
import com.example.dummysocial.Model.Tags.Tag_response
import com.example.dummysocial.Model.User.User_response
import com.example.dummysocial.Model.UserDetails.User_details_response

sealed class ApiState {
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()
    object Empty : ApiState()

    class SuccessUserList(val data: User_response) : ApiState()
    class SuccessPost(val data: Post_response) : ApiState()
    class SuccessPostDetails(val data: PostDetails_response) : ApiState()
    class SuccessPostComment(val data: PostComment_response) : ApiState()
    class SuccessUserDetails(val data: User_details_response) : ApiState()
    class SuccessTags(val data: Tag_response) : ApiState()

}
