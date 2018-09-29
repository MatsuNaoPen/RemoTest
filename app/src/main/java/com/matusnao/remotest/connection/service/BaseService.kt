package com.matusnao.household.connection.api.service

import android.content.Context
import com.matusnao.remotest.connection.RemoInterceptor
import com.matusnao.remotest.preference.ConstValues
import com.matusnao.remotest.preference.PreferenceUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by DevUser on 2017/12/21.
 */
open class BaseService {
    companion object {
        fun getRetrofit(context: Context): Retrofit {
            val apiUrl = "https://api.nature.global/"
            val token = PreferenceUtils.getString(context, ConstValues.PREF_KEY_REMO_TOKEN)
            val client = OkHttpClient.Builder()
                    .addInterceptor(RemoInterceptor(token))
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(apiUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit
        }
    }
}