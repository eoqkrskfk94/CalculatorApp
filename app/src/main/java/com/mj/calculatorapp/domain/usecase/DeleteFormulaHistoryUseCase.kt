package com.mj.calculatorapp.domain.usecase

import com.mj.calculatorapp.domain.repository.FormulaRepository
import com.mj.calculatorapp.util.Result
import javax.inject.Inject

class DeleteFormulaHistoryUseCase @Inject constructor(
    private val formulaRepository: FormulaRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return formulaRepository.deleteFormulaHistory()
    }

}