package com.mj.calculatorapp.domain.repository

import com.mj.calculatorapp.domain.model.Formula
import com.mj.calculatorapp.util.Result

interface FormulaRepository {

    suspend fun getAllFormulaHistory(): Result<List<Formula>>

    suspend fun insertFormulaToHistory(formula: Formula): Result<Unit>

    suspend fun deleteFormulaHistory(): Result<Unit>

}