package com.matusnao.remotest.connection.request

import com.matusnao.remotest.connection.response.ResponseGetUsersMe
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by DevUser on 2018/05/04.
 */
interface RequestGetUsersMe {
    @GET("/1/users/me")
    fun request(): Call<ResponseGetUsersMe>
}

