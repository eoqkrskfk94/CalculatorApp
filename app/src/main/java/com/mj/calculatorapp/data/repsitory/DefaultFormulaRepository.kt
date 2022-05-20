package com.mj.calculatorapp.data.repsitory

import com.mj.calculatorapp.data.datasource.FormulaDao
import com.mj.calculatorapp.domain.model.Formula
import com.mj.calculatorapp.domain.repository.FormulaRepository
import com.mj.calculatorapp.util.Result
import com.mj.calculatorapp.util.provider.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultFormulaRepository @Inject constructor(
    private val formulaDao: FormulaDao,
    private val dispatcherProvider: DispatcherProvider
) : FormulaRepository {

    override suspend fun getAllFormulaHistory(): Result<List<Formula>> = withContext(dispatcherProvider.io) {
        return@withContext try {
            val result = formulaDao.getAll()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun insertFormulaToHistory(formula: Formula): Result<Unit> = withContext(dispatcherProvider.io) {
        return@withContext try {
            val result = formulaDao.insert(formula)
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteFormulaHistory(): Result<Unit> = withContext(dispatcherProvider.io) {
        return@withContext try {
            val result = formulaDao.deleteAll()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


}