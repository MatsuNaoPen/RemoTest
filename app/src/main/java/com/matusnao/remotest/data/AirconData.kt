package com.matusnao.remotest.data

import java.io.Serializable

/**
 * Created by DevUser on 2018/05/05.
 */
data class AirconData(
        val range: RangeData,
        val tempUnit:String
) : Serializable