package com.mj.calculatorapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mj.calculatorapp.domain.usecase.CalculateEquationUseCase
import com.mj.calculatorapp.presentation.base.BaseViewModel
import com.mj.calculatorapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val calculateEquationUseCase: CalculateEquationUseCase
) : BaseViewModel() {

    private val _equationLiveData = MutableLiveData<String>()
    val equationLiveData: LiveData<String> = _equationLiveData

    private val _resultLiveData = MutableLiveData<String>()
    val resultLiveData: LiveData<String> = _resultLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private var inputMode = true

    init {
        _equationLiveData.value = ""
    }

    fun addToEquation(symbol: String) {
        if (!inputMode) {
            _errorLiveData.value = "AC를 눌러 초기화 후 사용해주세요"
            return
        }

        when (symbol) {
            "+", "–", "×", "÷" -> {
                _equationLiveData.value = _equationLiveData.value.plus(" $symbol ")
            }
            else -> {
                _equationLiveData.value = _equationLiveData.value.plus(symbol)
            }
        }
    }

    fun clearInput() {
        _equationLiveData.value = ""
        inputMode = true
    }

    fun getResult(equation: String) {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Exception thrown in one of the children: $exception")
        }

        inputMode = false
        viewModelScope.launch(handler) {
            when (val result = calculateEquationUseCase(equation)) {
                is Result.Success -> {
                    _resultLiveData.value = result.data ?: ""
                }
                is Result.Error -> {
                    _errorLiveData.value = "연산하는데 오류가 발생했습니다. 초기화 후 다시 입력해주세요"
                }
            }
        }
    }


}
