package com.matusnao.household.connection.api.service

import com.matusnao.remotest.connection.interceptor.RemoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by DevUser on 2017/12/21.
 */
open class BaseService {
    companion object {
        fun getRetrofit(): Retrofit {
            val apiUrl = "https://api.nature.global/"

            val client = OkHttpClient.Builder()
                    .addInterceptor(RemoInterceptor())
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