package com.mj.calculatorapp.data.database

import android.content.Context
import android.content.SharedPreferences

class FormulaPreference(private val context: Context) {

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

    fun setRecentFormula(formula: String) {
        editor.putString(FORMULA, formula)
        editor.apply()
    }

    fun getRecentFormula(): String {
        return prefs.getString(FORMULA, DEFAULT_VALUE_STRING) ?: ""
    }

    fun removeRecentFormula() {
        editor.remove(FORMULA)
        editor.apply()
    }

}