package com.matusnao.remotest.data

import java.io.Serializable

/**
 * Created by DevUser on 2018/05/05.
 */
data class AirconModeData(
        val temp: List<String>,
        val vol: List<String>,
        val dir: List<String>
) : Serializable