package com.matusnao.remotest.connection.service

import android.util.Log
import com.matusnao.remotest.connection.response.ResponseGetUsersMe
import com.matusnao.remotest.view.vcInterface.RemoCallback
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
            override fun onResponse(call: Call<ResponseGetUsersMe>?, response: Response<ResponseGetUsersMe>?) {
                response?.let {
                    callback.updateResultArea(getMessage(it))
                    callback.updateLogArea(getLog(it))
                }
            }

            override fun onFailure(call: Call<ResponseGetUsersMe>?, t: Throwable?) {
                Log.d(TAG, "on onFailure:" + t.toString())
                callback.updateLogArea(t.toString())
            }
        }
    }

    private fun getMessage(response: Response<ResponseGetUsersMe>): String {
        val builder = StringBuilder()
        when (response.code()) {
            200 -> {
                response.body()?.let {
                    builder.append("id : " + it.id + "\n")
                    builder.append("nickname : " + it.nickname)
                }
            }
        }

        if (builder.isEmpty()) {
            builder.append("error")
        }
        return builder.toString()
    }

    private fun getLog(response: Response<ResponseGetUsersMe>): String {
        when (response.code()) {
            200 -> {
                response.body()?.let {
                    return response.body().toString()
                }
            }
        }

        return response.errorBody()?.toString() ?: ""
    }

}