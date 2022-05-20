package com.mj.calculatorapp.presentation.main.calculator


import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.mj.calculatorapp.R
import com.mj.calculatorapp.databinding.FragmentCalculatorBinding
import com.mj.calculatorapp.presentation.base.BaseFragment
import com.mj.calculatorapp.presentation.main.MainViewModel
import com.mj.calculatorapp.presentation.main.calculator.adapter.HistoryListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CalculatorFragment : BaseFragment<CalculatorViewModel, FragmentCalculatorBinding>() {

    override val viewModel: CalculatorViewModel by viewModels()
    override fun getViewBinding(): FragmentCalculatorBinding = FragmentCalculatorBinding.inflate(layoutInflater)

    private lateinit var historyListAdapter: HistoryListAdapter

    override fun initViews() {
        super.initViews()
        setInputTextView()
        setCalculatorButtons()
        setHistoryRecyclerView()
        setHistoryCloseButton()
        setHistoryDeleteButton()

    }


    override fun observeData() {
        viewModel.formulaLiveData.observe(viewLifecycleOwner) {
            println(it)
            binding.textviewResult.text = it
        }

        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            binding.textviewResult.text = it
        }

        viewModel.historyLiveData.observe(viewLifecycleOwner) {
            historyListAdapter.submitList(it)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setInputTextView() {
        binding.textviewResult.movementMethod = ScrollingMovementMethod()
    }

    private fun setCalculatorButtons() = with(binding) {
        button0.setOnClickListener { viewModel.addToFormula("0") }
        button1.setOnClickListener { viewModel.addToFormula("1") }
        button2.setOnClickListener { viewModel.addToFormula("2") }
        button3.setOnClickListener { viewModel.addToFormula("3") }
        button4.setOnClickListener { viewModel.addToFormula("4") }
        button5.setOnClickListener { viewModel.addToFormula("5") }
        button6.setOnClickListener { viewModel.addToFormula("6") }
        button7.setOnClickListener { viewModel.addToFormula("7") }
        button8.setOnClickListener { viewModel.addToFormula("8") }
        button9.setOnClickListener { viewModel.addToFormula("9") }
        buttonDot.setOnClickListener { viewModel.addToFormula(".") }
        buttonPlus.setOnClickListener { viewModel.addToFormula(getString(R.string.plus)) }
        buttonMinus.setOnClickListener { viewModel.addToFormula(getString(R.string.minus)) }
        buttonMultiply.setOnClickListener { viewModel.addToFormula(getString(R.string.multiply)) }
        buttonDivide.setOnClickListener { viewModel.addToFormula(getString(R.string.divide)) }
        buttonAc.setOnClickListener { viewModel.clearInput() }
        buttonHistory.setOnClickListener { openHistoryList() }
        buttonResult.setOnClickListener { viewModel.getResult(textviewResult.text.toString()) }
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


    companion object {
        const val TAG = "CalculatorFragment"
        fun newInstance() = CalculatorFragment()
    }


}