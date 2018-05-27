package com.matusnao.remotest.connection.request

import com.matusnao.remotest.connection.response.ResponseGetAppliances
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by DevUser on 2018/05/05.
 */
interface  RequestGetAppliances{
    @GET("/1/appliances")
    fun request(): Call<List<ResponseGetAppliances>>
}