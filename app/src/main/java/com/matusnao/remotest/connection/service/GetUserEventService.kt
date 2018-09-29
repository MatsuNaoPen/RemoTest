package com.matusnao.remotest.connection.service

import android.util.Log
import com.matusnao.remotest.connection.response.ResponseGetUsersMe
import com.matusnao.remotest.view.VCInterface.RemoCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by DevUser on 2018/09/29.
 */
class GetUserEventService(val callback: RemoCallback) {
    private val TAG = javaClass.name

    fun getService(): Callback<ResponseGetUsersMe> {
        return object : Callback<ResponseGetUsersMe> {
            override fun onResponse(call: Call<ResponseGetUsersMe>?, responseGet: Response<ResponseGetUsersMe>?) {
                val message = when (responseGet!!.code()) {
                    200 -> {
                        responseGet.body()?.toString() ?: ""
                    }
                    else -> {
                        responseGet.errorBody()?.string() ?: ""
                    }
                }
                Log.d(TAG, "on Response:" + message)
                callback.updateResultArea(message)
            }

            override fun onFailure(call: Call<ResponseGetUsersMe>?, t: Throwable?) {
                Log.d(TAG, "on onFailure:" + t.toString())
                callback.updateResultArea(t.toString())
            }
        }
    }
}