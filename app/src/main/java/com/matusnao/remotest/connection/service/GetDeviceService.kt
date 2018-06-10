package com.matusnao.remotest.connection.service

import android.bluetooth.BluetoothClass
import android.content.ContentValues.TAG
import android.util.Log
import com.matusnao.remotest.connection.response.ResponseGetDevices
import com.matusnao.remotest.view.VCInterface.DeviceUpdateEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by DevUser on 2018/06/10.
 */

class GetDeviceService(val event: DeviceUpdateEvent) {
    fun getService(): Callback<List<ResponseGetDevices>> {
        return object : Callback<List<ResponseGetDevices>> {
            override fun onResponse(call: Call<List<ResponseGetDevices>>?, responseGet: Response<List<ResponseGetDevices>>?) {
                when (responseGet!!.code()) {
                    200 -> {
                        Log.d(TAG, "on Response:" + responseGet.toString())
                        val result: List<ResponseGetDevices> = responseGet.body()!!
                        Log.d(TAG, result.toString())
                        event.updateResultArea(result.toString())
                    }
                    else ->
                        Log.d(TAG, "on Response:" + responseGet.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<List<ResponseGetDevices>>?, t: Throwable?) {
                Log.d(TAG, "on onFailure:" + t.toString())
                event.updateResultArea(t.toString())
            }
        }
    }
}