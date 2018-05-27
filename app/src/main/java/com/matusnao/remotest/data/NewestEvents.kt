package com.matusnao.remotest.data
import java.io.Serializable

/**
 * Created by DevUser on 2018/05/05.
 */
data class NewestEvents(
        val te: Values,
        val hu: Values
) : Serializable