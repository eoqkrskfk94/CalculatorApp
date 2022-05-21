package com.mj.calculatorapp.presentation.main.calculator


import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mj.calculatorapp.R
import com.mj.calculatorapp.databinding.FragmentCalculatorBinding
import com.mj.calculatorapp.presentation.base.BaseFragment
import com.mj.calculatorapp.presentation.main.MainViewModel
import com.mj.calculatorapp.presentation.main.calculator.adapter.HistoryListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class CalculatorFragment : BaseFragment<CalculatorViewModel, FragmentCalculatorBinding>() {

    override val viewModel: CalculatorViewModel by viewModels()
    override fun getViewBinding(): FragmentCalculatorBinding = FragmentCalculatorBinding.inflate(layoutInflater)

    private lateinit var historyListAdapter: HistoryListAdapter

    override fun initViews() {
        super.initViews()
        setInputTextView()

        setNumberButtons()
        setOperatorButtons()

        setAcButton()
        setResultButton()

        setHistoryButton()
        setHistoryCloseButton()
        setHistoryDeleteButton()

        setHistoryRecyclerView()
    }


    override fun observeData() {

        viewModel.formulaTextLiveData.observe(viewLifecycleOwner) {
            binding.textviewResult.text = it
        }

        viewModel.historyLiveData.observe(viewLifecycleOwner) {
            historyListAdapter.submitList(it)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.errorFlow.collectLatest {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setInputTextView() {
        binding.textviewResult.movementMethod = ScrollingMovementMethod()
    }


    private fun setNumberButtons() = with(binding) {
        button0.setOnClickListener { viewModel.insertNumberToFormula("0") }
        button1.setOnClickListener { viewModel.insertNumberToFormula("1") }
        button2.setOnClickListener { viewModel.insertNumberToFormula("2") }
        button3.setOnClickListener { viewModel.insertNumberToFormula("3") }
        button4.setOnClickListener { viewModel.insertNumberToFormula("4") }
        button5.setOnClickListener { viewModel.insertNumberToFormula("5") }
        button6.setOnClickListener { viewModel.insertNumberToFormula("6") }
        button7.setOnClickListener { viewModel.insertNumberToFormula("7") }
        button8.setOnClickListener { viewModel.insertNumberToFormula("8") }
        button9.setOnClickListener { viewModel.insertNumberToFormula("9") }
        buttonDot.setOnClickListener { viewModel.insertNumberToFormula(".") }
    }

    private fun setOperatorButtons() = with(binding) {
        buttonPlus.setOnClickListener { viewModel.insertOperatorToFormula(getString(R.string.plus)) }
        buttonMinus.setOnClickListener { viewModel.insertOperatorToFormula(getString(R.string.minus)) }
        buttonMultiply.setOnClickListener { viewModel.insertOperatorToFormula(getString(R.string.multiply)) }
        buttonDivide.setOnClickListener { viewModel.insertOperatorToFormula(getString(R.string.divide)) }
    }

    private fun setAcButton() = with(binding) {
        buttonAc.setOnClickListener { viewModel.clearInput() }
    }

    private fun setResultButton() = with(binding) {
        buttonResult.setOnClickListener { viewModel.getResult(textviewResult.text.toString()) }
    }

    private fun setHistoryButton() = with(binding) {
        buttonHistory.setOnClickListener { openHistoryList() }
    }

    private fun setHistoryRecyclerView() = with(binding) {
        historyListAdapter = HistoryListAdapter {
            viewModel.setFormula(it.content)
            viewModel.setInputMode(true)
            linearlayoutCalculator.isVisible = true
            constraintlayoutHistory.isVisible = false
        }
        recyclerviewHistory.adapter = historyListAdapter
    }

    private fun openHistoryList() = with(binding) {
        linearlayoutCalculator.isVisible = false
        constraintlayoutHistory.isVisible = true
        viewModel.getHistory()
    }

    private fun setHistoryCloseButton() = with(binding) {
        imageviewClose.setOnClickListener {
            linearlayoutCalculator.isVisible = true
            constraintlayoutHistory.isVisible = false
        }
    }

    private fun setHistoryDeleteButton() = with(binding) {
        imageviewDelete.setOnClickListener {
            viewModel.deleteHistory()
        }
    }


    override fun onStop() {
        super.onStop()
        viewModel.saveRecentFormula(binding.textviewResult.text.toString())
    }


    companion object {
        const val TAG = "CalculatorFragment"
        fun newInstance() = CalculatorFragment()
    }


}