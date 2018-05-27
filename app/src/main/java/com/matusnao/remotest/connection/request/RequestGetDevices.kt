package com.matusnao.remotest.connection.request

import com.matusnao.remotest.connection.response.ResponseGetDevices
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by DevUser on 2018/05/04.
 */
interface RequestGetDevices {
    @GET("/1/devices")
    fun request(): Call<List<ResponseGetDevices>>
}