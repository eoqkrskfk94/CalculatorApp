package com.mj.calculatorapp.presentation.main.calculator

import com.mj.calculatorapp.data.repository.FakeFormulaRepository
import com.mj.calculatorapp.util.InstantTaskExecutorExtension
import com.mj.calculatorapp.util.MainCoroutineExtension
import com.mj.calculatorapp.domain.usecase.*
import com.mj.calculatorapp.util.TestDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat
import com.mj.calculatorapp.data.model.FormulaEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension


@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class)
class CalculatorViewModelTest {


    companion object {
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }

    private lateinit var viewModel: CalculatorViewModel
    private lateinit var fakeFormulaRepository: FakeFormulaRepository
    private lateinit var testDispatcherProvider: TestDispatcherProvider


    @BeforeEach
    fun setUp() {
        testDispatcherProvider = TestDispatcherProvider(mainCoroutineExtension.testDispatcher)
        fakeFormulaRepository = FakeFormulaRepository()
        val getFormulaCalculationUseCase = GetFormulaCalculationUseCase(testDispatcherProvider)
        val insertFormulaToHistoryUseCase = InsertFormulaToHistoryUseCase(fakeFormulaRepository)
        val getFormulaHistoryUseCase = GetFormulaHistoryUseCase(fakeFormulaRepository)
        val deleteFormulaHistoryUseCase = DeleteFormulaHistoryUseCase(fakeFormulaRepository)
        val saveRecentFormulaUseCase = SaveRecentFormulaUseCase(fakeFormulaRepository)
        val getRecentFormulaUseCase = GetRecentFormulaUseCase(fakeFormulaRepository)
        val deleteRecentFormulaUseCase = DeleteRecentFormulaUseCase(fakeFormulaRepository)
        viewModel = CalculatorViewModel(
            getFormulaCalculationUseCase,
            insertFormulaToHistoryUseCase,
            getFormulaHistoryUseCase,
            deleteFormulaHistoryUseCase,
            saveRecentFormulaUseCase,
            getRecentFormulaUseCase,
            deleteRecentFormulaUseCase
        )
        viewModel.clearInput()
    }

    @Test
    fun setRecentFormulaTest() {
        val expectedAnswer = "2 ?? 1.5 + 6 ?? 2"
        fakeFormulaRepository.recentFormula = expectedAnswer
        viewModel.setRecentFormula()

        assertThat(viewModel.formulaTextLiveData.value).isEqualTo(expectedAnswer)
    }

    @Test
    fun setFormulaTest() {
        val expectedAnswer = "1 + 1 ?? 5.555555"
        viewModel.setFormula(expectedAnswer)

        assertThat(viewModel.formulaTextLiveData.value).isEqualTo(expectedAnswer)
    }

    @Test
    fun clearInputTest() {
        viewModel.setFormula("12345")
        viewModel.clearInput()
        assertThat(viewModel.formulaTextLiveData.value).isEqualTo("")
    }

    @Test
    fun insertNumberToFormulaTest() {
        val expectedAnswer = "15.2"
        viewModel.insertNumberToFormula("1")
        viewModel.insertNumberToFormula("5")
        viewModel.insertNumberToFormula(".")
        viewModel.insertNumberToFormula("2")

        assertThat(viewModel.formulaTextLiveData.value).isEqualTo(expectedAnswer)
    }

    @Test
    fun insertOperatorToFormula() {
        val expectedAnswer = "631 ?? 5"
        viewModel.insertNumberToFormula("6")
        viewModel.insertNumberToFormula("3")
        viewModel.insertNumberToFormula("1")
        viewModel.insertOperatorToFormula("??")
        viewModel.insertNumberToFormula("5")

        assertThat(viewModel.formulaTextLiveData.value).isEqualTo(expectedAnswer)
    }

    @Test
    fun `valid formula returns correct answer`() {
        val expectedAnswer = "6"
        viewModel.getResult("2 ?? 1.5 + 6 ?? 2")

        assertThat(viewModel.formulaTextLiveData.value).isEqualTo(expectedAnswer)
    }

    @Test
    fun `invalid formula returns error message`() = mainCoroutineExtension.runBlockingTest {
        val expectedAnswer = "??????????????? ????????? ??????????????????. ????????? ??? ?????? ??????????????????"
        var resultAnswer = ""

        val job = launch {
            viewModel.errorFlow.collectLatest {
                resultAnswer = it

            }
        }
        viewModel.getResult("+ 2 ?? 1.5 + 6 ?? ?? 2")

        job.cancel()
        assertThat(resultAnswer).isEqualTo(expectedAnswer)
    }

    @Test
    fun getHistoryTest() {
        val expectedAnswer = fakeFormulaRepository.formulas
        viewModel.getHistory()
        assertThat(viewModel.historyLiveData.value).isEqualTo(expectedAnswer)
    }

    @Test
    fun deleteHistoryTest() {
        viewModel.deleteHistory()
        assertThat(fakeFormulaRepository.formulas).isEqualTo(listOf<FormulaEntity>())
    }


}