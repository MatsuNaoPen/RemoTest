package com.matusnao.remotest.connection.response

import com.matusnao.remotest.data.NewestEvents
import java.io.Serializable

/**
 * Created by DevUser on 2018/05/05.
 */
data class ResponseGetDevices(
        val id: String,
        val name: String,
        val temperature_offset: String,
        val humidity_offset: String,
        val created_at: String,
        val updated_at: String,
        val firmware_version: String,
        val newest_events: NewestEvents
) : Serializable
