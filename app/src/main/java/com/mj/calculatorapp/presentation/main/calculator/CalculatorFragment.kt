package com.mj.calculatorapp.presentation.main.calculator


import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mj.calculatorapp.databinding.FragmentCalculatorBinding
import com.mj.calculatorapp.presentation.base.BaseFragment
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
        setDatabinding()

        setInputTextViewScroll(binding.textviewResult)
        historyListAdapter = getHistoryListAdapter()
        binding.recyclerviewHistory.adapter = historyListAdapter
    }

    override fun observeData() {

        viewModel.historyLiveData.observe(viewLifecycleOwner) {
            historyListAdapter.submitList(it)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.errorFlow.collectLatest {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setDatabinding() = with(binding) {
        vm = viewModel
        fragment = this@CalculatorFragment
        lifecycleOwner = viewLifecycleOwner
    }

    private fun setInputTextViewScroll(textView: TextView) {
        textView.movementMethod = ScrollingMovementMethod()
    }


    private fun getHistoryListAdapter(): HistoryListAdapter = with(binding) {
        return@with HistoryListAdapter {
            viewModel.setFormula(it.content)
            viewModel.setInputMode(true)
            linearlayoutCalculator.isVisible = true
            constraintlayoutHistory.isVisible = false
        }
    }

    fun openHistoryList() = with(binding) {
        linearlayoutCalculator.isVisible = false
        constraintlayoutHistory.isVisible = true
        viewModel.getHistory()
    }

    fun closeHistoryList() = with(binding) {
        linearlayoutCalculator.isVisible = true
        constraintlayoutHistory.isVisible = false
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