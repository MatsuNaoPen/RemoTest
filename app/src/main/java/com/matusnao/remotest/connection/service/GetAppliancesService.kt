package com.matusnao.remotest.connection.service

import android.util.Log
import com.matusnao.remotest.connection.response.ResponseGetAppliances
import com.matusnao.remotest.data.SignalListData
import com.matusnao.remotest.data.Signals
import com.matusnao.remotest.view.vcInterface.RemoCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by DevUser on 2018/09/29.
 */
class GetAppliancesService(val callback: RemoCallback) {
    private val TAG = this.javaClass.name

    fun getService(): Callback<List<ResponseGetAppliances>> {
        return object : Callback<List<ResponseGetAppliances>> {
            override fun onResponse(call: Call<List<ResponseGetAppliances>>?,
                                    responseGet: Response<List<ResponseGetAppliances>>?) {
                when (responseGet?.code()) {
                    200 -> {
                        Log.d(TAG, "on Response:" + responseGet.toString())
                        responseGet.body()?.let {
                            Log.d(TAG, it.toString())
                            callback.showSignalArea(getSignalList(it))
                            callback.updateLogArea(it.toString())
                        }
                    }
                    else ->
                        responseGet?.let {
                            it.errorBody()?.let {
                                Log.d(TAG, "on Response:" + it.string())
                            }
                        }
                }
            }

            override fun onFailure(call: Call<List<ResponseGetAppliances>>?, t: Throwable?) {
                Log.d(TAG, "on onFailure:" + t.toString())
                callback.updateLogArea(t.toString())
            }
        }
    }

    private fun getSignalList(result: List<ResponseGetAppliances>): SignalListData {
        val map: MutableMap<String, List<Signals>> = mutableMapOf()
        for (tmp in result) {
            map.put(tmp.nickname, tmp.signals)
        }
        return SignalListData(map)
    }

}
