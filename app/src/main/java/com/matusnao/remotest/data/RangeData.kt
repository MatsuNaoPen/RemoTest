package com.matusnao.remotest.data

import java.io.Serializable

/**
 * Created by DevUser on 2018/05/05.
 */
data class RangeData(
        val modes:ModesData,
        val fixedButtons: List<String>
):Serializable