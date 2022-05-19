package com.mj.calculatorapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mj.calculatorapp.domain.usecase.CalculateFormulaUseCase
import com.mj.calculatorapp.domain.usecase.InsertFormulaToHistoryUseCase
import com.mj.calculatorapp.presentation.base.BaseViewModel
import com.mj.calculatorapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val calculateFormulaUseCase: CalculateFormulaUseCase,
    private val insertFormulaToHistoryUseCase: InsertFormulaToHistoryUseCase
) : BaseViewModel() {

    private val _formulaLiveData = MutableLiveData<String>()
    val formulaLiveData: LiveData<String> = _formulaLiveData

    private val _resultLiveData = MutableLiveData<String>()
    val resultLiveData: LiveData<String> = _resultLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private var inputMode = true

    init {
        _formulaLiveData.value = ""
    }

    fun addToFormula(symbol: String) {
        if (!inputMode) {
            _errorLiveData.value = "AC를 눌러 초기화 후 사용해주세요"
            return
        }

        when (symbol) {
            "+", "–", "×", "÷" -> {
                _formulaLiveData.value = _formulaLiveData.value.plus(" $symbol ")
            }
            else -> {
                _formulaLiveData.value = _formulaLiveData.value.plus(symbol)
            }
        }
    }

    fun clearInput() {
        _formulaLiveData.value = ""
        inputMode = true
    }

    fun getResult(formula: String) {
        if (formula.split(" ").size == 1) {
            return
        }

        val handler = CoroutineExceptionHandler { _, exception ->
            println("Exception thrown in one of the children: $exception")
        }

        inputMode = false
        viewModelScope.launch(handler) {
            when (val result = calculateFormulaUseCase(formula)) {
                is Result.Success -> {
                    _resultLiveData.value = result.data ?: ""
                    insertFormulaToHistoryUseCase(formula)
                }
                is Result.Error -> {
                    _errorLiveData.value = "연산하는데 오류가 발생했습니다. 초기화 후 다시 입력해주세요"
                }
            }
        }
    }


}
