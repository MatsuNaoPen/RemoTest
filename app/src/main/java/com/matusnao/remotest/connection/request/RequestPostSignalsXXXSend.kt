package com.matusnao.remotest.connection.request

import com.matusnao.remotest.connection.response.ResponsePostSignalsXXXSend
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by DevUser on 2018/05/05.
 */
interface RequestPostSignalsXXXSend {
    @POST("/1/signals/{signal}/send")
    fun request(@Path("signal") signalId: String): Call<ResponsePostSignalsXXXSend>
}
