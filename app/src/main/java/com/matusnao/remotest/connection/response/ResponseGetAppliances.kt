package com.matusnao.remotest.connection.response

import com.matusnao.remotest.data.*
import java.io.Serializable

/**
 * Created by DevUser on 2018/05/05.
 */
data class ResponseGetAppliances(
        val id: String,
        val device: DeviceData?,
        val model: ModelData,
        val nickname: String,
        val image: String,
        val type: String,
        val settings: SettingsData,
        val aircon: AirconData,
        val signals: List<Signals>
) : Serializable


