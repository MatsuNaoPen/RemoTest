package com.matusnao.remotest.preference

import android.content.Context
import android.content.SharedPreferences

/**
 * SharePreference系のUtils
 * Created by DevUser on 2018/09/29.
 */
object PreferenceUtils {
    private val prefName = "BasePreference"

    fun getString(context: Context, key: String): String =
            getSharedPreference(context).getString(key, ConstValues.DEFAULT_VALUE_PREF_NO_DATA_STRING)

    fun putString(context: Context, key: String, value: String) {
        val preference = getSharedPreference(context)
        val editor = preference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getBoolean(context: Context, key: String) =
            getSharedPreference(context).getBoolean(key, false)

    fun putBoolean(context: Context, key: String, value: Boolean) {
        val preference = getSharedPreference(context)
        val editor = preference.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun isExist(context: Context, key: String): Boolean =
            getSharedPreference(context).getString(key, ConstValues.DEFAULT_VALUE_PREF_NO_DATA_STRING) != ConstValues.DEFAULT_VALUE_PREF_NO_DATA_STRING

    private fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }
}