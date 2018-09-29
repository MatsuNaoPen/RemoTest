package com.matusnao.remotest.connection.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by DevUser on 2018/05/04.
 */
class RemoInterceptor() : Interceptor {
    val auth_token = "write yourt token"

    override fun intercept(chain: Interceptor.Chain): Response {
        val orig = chain.request()
        val request = orig.newBuilder()
                .header("Authorization", "Bearer " + auth_token)
                .build()

        return chain.proceed(request)
    }
}