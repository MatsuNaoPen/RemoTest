package com.matusnao.remotest.data

import java.io.Serializable

/**
 * Created by DevUser on 2018/05/05.
 */
data class ModesData(
        val cool: AirconModeData,
        val warm: AirconModeData,
        val dry: AirconModeData,
        val blow: AirconModeData,
        val auto: AirconModeData
) : Serializable