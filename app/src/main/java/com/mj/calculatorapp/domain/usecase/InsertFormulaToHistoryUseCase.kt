package com.mj.calculatorapp.domain.usecase

import com.mj.calculatorapp.domain.model.Formula
import com.mj.calculatorapp.domain.repository.FormulaRepository
import javax.inject.Inject

class InsertFormulaToHistoryUseCase @Inject constructor(
    private val formulaRepository: FormulaRepository
) {

    suspend operator fun invoke(formula: String) {
        formulaRepository.insertFormulaToHistory(Formula(content = formula))
    }

}