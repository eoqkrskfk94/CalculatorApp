package com.mj.calculatorapp.presentation.main.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mj.calculatorapp.domain.model.Formula
import com.mj.calculatorapp.domain.usecase.*
import com.mj.calculatorapp.presentation.base.BaseViewModel
import com.mj.calculatorapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val getFormulaCalculationUseCase: GetFormulaCalculationUseCase,
    private val insertFormulaToHistoryUseCase: InsertFormulaToHistoryUseCase,
    private val getFormulaHistoryUseCase: GetFormulaHistoryUseCase,
    private val deleteFormulaHistoryUseCase: DeleteFormulaHistoryUseCase,
    private val saveRecentFormulaUseCase: SaveRecentFormulaUseCase,
    private val getRecentFormulaUseCase: GetRecentFormulaUseCase,
    private val deleteRecentFormulaUseCase: DeleteRecentFormulaUseCase
) : BaseViewModel() {

    private val _formulaTextLiveData = MutableLiveData<String>()
    val formulaTextLiveData: LiveData<String> = _formulaTextLiveData

    private val _historyLiveData = MutableLiveData<List<Formula>>()
    val historyLiveData: LiveData<List<Formula>> = _historyLiveData

    private val _historyOpenLiveData = MutableLiveData<Boolean>()
    val historyOpenLiveData: LiveData<Boolean> = _historyOpenLiveData

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    private var inputMode = true

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("Exception thrown in one of the children: $exception")
    }

    init {
        _formulaTextLiveData.value = ""
        setRecentFormula()
    }



    fun setRecentFormula() {
        viewModelScope.launch(handler) {
            when (val result = getRecentFormulaUseCase()) {
                is Result.Success -> {
                    _formulaTextLiveData.value = result.data ?: ""
                }
                is Result.Error -> {
                    _errorFlow.emit("?????? ????????? ??????????????? ????????? ??????????????????")
                }
            }
        }
    }

    fun setInputMode(mode: Boolean) {
        inputMode = mode
    }

    fun setFormula(formula: String) {
        _formulaTextLiveData.value = formula
    }

    fun insertNumberToFormula(number: String) {
        viewModelScope.launch {
            if (!inputMode) {
                _errorFlow.emit("AC??? ?????? ????????? ??? ??????????????????")
                return@launch
            }
            _formulaTextLiveData.value = _formulaTextLiveData.value.plus(number)
        }
    }

    fun insertOperatorToFormula(operator: String) {
        viewModelScope.launch {
            if (!inputMode) {
                _errorFlow.emit("AC??? ?????? ????????? ??? ??????????????????")
                return@launch
            }
            _formulaTextLiveData.value = _formulaTextLiveData.value.plus(" $operator ")
        }
    }

    fun clearInput() {
        _formulaTextLiveData.value = ""
        inputMode = true
    }

    fun getResult(formula: String) {
        if (formula.split(" ").size == 1) {
            return
        }

        inputMode = false

        viewModelScope.launch(handler) {
            when (val result = getFormulaCalculationUseCase(formula)) {
                is Result.Success -> {
                    _formulaTextLiveData.value = result.data ?: ""
                    insertFormulaToHistoryUseCase(formula)
                    deleteRecentFormulaUseCase()
                }
                is Result.Error -> {
                    _errorFlow.emit("??????????????? ????????? ??????????????????. ????????? ??? ?????? ??????????????????")
                }
            }
        }
    }

    fun saveRecentFormula() {
        if (!inputMode) return

        viewModelScope.launch(handler) {
            when (saveRecentFormulaUseCase(_formulaTextLiveData.value ?: "")) {
                is Result.Success -> {}
                is Result.Error -> {}
            }
        }
    }

    fun openHistory() {
        _historyOpenLiveData.value = true
        getHistory()
    }

    fun closeHistory() {
        _historyOpenLiveData.value = false
    }


    fun getHistory() {
        viewModelScope.launch(handler) {
            when (val result = getFormulaHistoryUseCase()) {
                is Result.Success -> {
                    _historyLiveData.value = result.data ?: listOf()
                }
                is Result.Error -> {
                    _errorFlow.emit("History??? ??????????????? ????????? ??????????????????.")
                }
            }
        }
    }

    fun deleteHistory() {
        viewModelScope.launch(handler) {
            when (deleteFormulaHistoryUseCase()) {
                is Result.Success -> {
                    _historyLiveData.value = listOf()
                }
                is Result.Error -> {
                    _errorFlow.emit("History??? ??????????????? ????????? ??????????????????.")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveRecentFormula()
    }
}
