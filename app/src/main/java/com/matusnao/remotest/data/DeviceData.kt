package com.matusnao.remotest.data

import java.io.Serializable

/**
 * Created by DevUser on 2018/05/05.
 */
data class DeviceData(
        val id: String,
        val name: String,
        val temperature_offset: String,
        val humidity_offset: String,
        val created_at: String,
        val updated_at: String,
        val firmware_version: String
) : Serializable