package com.matusnao.remotest.connection.service

import android.os.AsyncTask
import com.google.gson.Gson
import com.matusnao.remotest.connection.response.ResponseGetDevices
import com.matusnao.remotest.view.vcInterface.RemoCallback
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by DevUser on 2018/06/10.
 */
class GetDeviceServiceOld(val event: RemoCallback) {
    val auth_token = "へんこうする"

    fun getService(): AsyncTask<Void, Void, ResponseGetDevices> {
        return GetDeviceServiceOldAsyncTask()
    }

    open inner class GetDeviceServiceOldAsyncTask : AsyncTask<Void, Void, ResponseGetDevices>() {
        override fun doInBackground(vararg p0: Void?): ResponseGetDevices {
            val CONNECTION_TIMEOUT = 30 * 1000
            val READ_TIMEOUT = 30 * 1000

            val url = URL("https://api.nature.global/1/devices")
            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = CONNECTION_TIMEOUT
            conn.readTimeout = READ_TIMEOUT
            conn.requestMethod = "GET"
            conn.addRequestProperty("Authorization", "Bearer " + auth_token)
            conn.connect()
            val statusCode = conn.responseCode
            when (statusCode) {
                200 -> {
                    val result: StringBuilder = StringBuilder()
                    //responseの読み込み
                    val inputStram = conn.inputStream
                    val inReader = InputStreamReader(inputStram)
                    val bufferedReader = BufferedReader(inReader)
                    var line: String? = null
                    do {
                        line = bufferedReader.readLine()
                        if (line == null) {
                            break
                        }
                        result.append(line)
                    } while (true)

                    bufferedReader.close()
                    inReader.close()
                    inputStram.close()
                    // ResponseGetDevicesの構造的に単純にgson化しようとすると失敗する
                    return Gson().fromJson(result.toString(), ResponseGetDevices::class.java)
                }
                else -> {
                    return Gson().fromJson("", ResponseGetDevices::class.java)
                }
            }
        }
    }
}