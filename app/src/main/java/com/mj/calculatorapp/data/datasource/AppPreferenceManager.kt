package com.mj.calculatorapp.data.datasource

import android.content.Context
import android.content.SharedPreferences

class AppPreferenceManager(private val context: Context) {

    companion object {
        const val PREFERENCES_NAME = "calculator"
        private const val DEFAULT_VALUE_STRING = ""
        const val FORMULA = "formula"
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private val prefs by lazy { getPreferences(context) }

    private val editor by lazy { prefs.edit() }

    fun setString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?): String? {
        return prefs.getString(key, DEFAULT_VALUE_STRING)
    }

    fun removeString(key: String?) {
        editor.remove(key)
        editor.apply()
    }

}