package com.matusnao.remotest.data

import java.io.Serializable

/**
 * Created by DevUser on 2018/05/05.
 */
data class ModelData(
        val id: String,
        val manufacturer: String,
        val remote_name: String,
        val name: String,
        val image: String
) : Serializable