package com.mj.calculatorapp.domain.usecase

import com.mj.calculatorapp.data.datasource.AppPreferenceManager
import com.mj.calculatorapp.domain.model.Formula
import com.mj.calculatorapp.domain.repository.FormulaRepository
import com.mj.calculatorapp.util.Result
import javax.inject.Inject

class GetRecentFormulaUseCase @Inject constructor(
    private val appPreferenceManager: AppPreferenceManager
) {

    operator fun invoke(): String {
        return appPreferenceManager.getString(AppPreferenceManager.FORMULA) ?: ""
    }

}