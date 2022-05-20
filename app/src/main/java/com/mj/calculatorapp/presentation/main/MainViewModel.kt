package com.mj.calculatorapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mj.calculatorapp.domain.model.Formula
import com.mj.calculatorapp.domain.usecase.CalculateFormulaUseCase
import com.mj.calculatorapp.domain.usecase.DeleteFormulaHistoryUseCase
import com.mj.calculatorapp.domain.usecase.GetFormulaHistoryUseCase
import com.mj.calculatorapp.domain.usecase.InsertFormulaToHistoryUseCase
import com.mj.calculatorapp.presentation.base.BaseViewModel
import com.mj.calculatorapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {


}
