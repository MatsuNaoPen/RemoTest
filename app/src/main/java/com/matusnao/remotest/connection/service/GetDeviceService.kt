package com.matusnao.remotest.connection.service

import android.content.ContentValues.TAG
import android.util.Log
import com.matusnao.remotest.connection.response.ResponseGetDevices
import com.matusnao.remotest.connection.response.ResponseGetUsersMe
import com.matusnao.remotest.view.vcInterface.RemoCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by DevUser on 2018/06/10.
 */

class GetDeviceService(val callback: RemoCallback) {
    fun getService(): Callback<List<ResponseGetDevices>> {
        return object : Callback<List<ResponseGetDevices>> {
            override fun onResponse(call: Call<List<ResponseGetDevices>>?, response: Response<List<ResponseGetDevices>>?) {
                response?.let {
                    callback.updateResultArea(getMessage(it))
                    callback.updateLogArea(getLog(it))
                }
            }

            override fun onFailure(call: Call<List<ResponseGetDevices>>?, t: Throwable?) {
                Log.d(TAG, "on onFailure:" + t.toString())
                callback.updateLogArea(t.toString())
            }
        }
    }

    private fun getMessage(response: Response<List<ResponseGetDevices>>): String {
        val builder = StringBuilder()
        when (response.code()) {
            200 -> {
                response.body()?.let {
                    for (devices in it) {
                        builder.append("id : " + devices.id + "\n")
                        builder.append("  name : " + devices.name + "\n")
                        builder.append("  temperature_offset : " + devices.temperature_offset + "\n")
                        builder.append("  humidity_offset : " + devices.humidity_offset + "\n")
                        builder.append("  created_at : " + devices.created_at + "\n")
                        builder.append("  updated_at : " + devices.updated_at + "\n")
                        builder.append("  firmware_version : " + devices.firmware_version + "\n")
                        builder.append("  newest_events : \n")
                        builder.append("    te : " + devices.newest_events.te + "\n")
                        builder.append("    hu : " + devices.newest_events.hu + "\n")

                    }
                }
            }
        }

        if (builder.isEmpty()) {
            builder.append("error")
        }
        return builder.toString()
    }

    private fun getLog(response: Response<List<ResponseGetDevices>>): String {
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