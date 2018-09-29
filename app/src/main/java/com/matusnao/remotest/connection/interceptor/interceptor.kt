package com.matusnao.remotest.connection.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by DevUser on 2018/05/04.
 */
class RemoInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val orig = chain.request()
        val request = orig.newBuilder()
                .header("Authorization", "Bearer " + token)
                .build()

        return chain.proceed(request)
    }
}