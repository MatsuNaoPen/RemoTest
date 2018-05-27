package com.matusnao.remotest.connection.response

import java.io.Serializable

/**
 * Created by DevUser on 2018/05/04.
 */
data class ResponseGetUsersMe(
        val id: String,
        val nickname: String
):Serializable