package com.matusnao.remotest.data

import java.io.Serializable

/**
 * Created by DevUser on 2018/05/05.
 */
data class Values(
        val value: String,
        val created_at: String
) : Serializable