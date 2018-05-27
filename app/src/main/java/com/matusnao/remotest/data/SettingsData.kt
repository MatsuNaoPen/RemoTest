package com.matusnao.remotest.data

import java.io.Serializable

/**
 * Created by DevUser on 2018/05/05.
 */
data class SettingsData(
        val temp: String,
        val mode:String,
        val vol:String,
        val dir:String,
        val button:String
):Serializable