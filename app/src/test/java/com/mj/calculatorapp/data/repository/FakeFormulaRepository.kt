package com.mj.calculatorapp.data.repository

import com.mj.calculatorapp.data.model.FormulaEntity
import com.mj.calculatorapp.domain.model.Formula
import com.mj.calculatorapp.domain.repository.FormulaRepository
import com.mj.calculatorapp.util.Result

class FakeFormulaRepository : FormulaRepository {

    var formulas = mutableListOf(Formula("1 + 1"), Formula("2 × 1.5 + 6 ÷ 2"))
    var recentFormula = "2 × 1.5 + 6 ÷ 2"

    override suspend fun getAllFormulaHistory(): Result<List<Formula>> {
        return Result.Success(formulas)
    }

    override suspend fun insertFormulaToHistory(formula: Formula): Result<Unit> {
        return Result.Success(Unit)
    }

    override suspend fun deleteFormulaHistory(): Result<Unit> {
        formulas.clear()
        return Result.Success(Unit)
    }

    override suspend fun getResentFormula(): Result<String> {
        return Result.Success(recentFormula)
    }

    override suspend fun saveRecentFormula(formula: String): Result<Unit> {
        return Result.Success(Unit)
    }

    override suspend fun deleteRecentFormula(): Result<Unit> {
        return Result.Success(Unit)
    }
}