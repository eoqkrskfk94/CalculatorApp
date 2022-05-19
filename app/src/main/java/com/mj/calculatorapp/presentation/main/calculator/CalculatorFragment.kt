package com.mj.calculatorapp.presentation.main.calculator


import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.mj.calculatorapp.R
import com.mj.calculatorapp.databinding.FragmentCalculatorBinding
import com.mj.calculatorapp.presentation.base.BaseFragment
import com.mj.calculatorapp.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CalculatorFragment : BaseFragment<MainViewModel, FragmentCalculatorBinding>() {

    override val viewModel: MainViewModel by activityViewModels()
    override fun getViewBinding(): FragmentCalculatorBinding = FragmentCalculatorBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()
        setInputTextView()
        setCalculatorButtons()

    }

    override fun observeData() {
        viewModel.equationLiveData.observe(viewLifecycleOwner) {
            binding.textviewResult.text = it
        }

        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            binding.textviewResult.text = it
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setInputTextView() {
        binding.textviewResult.movementMethod = ScrollingMovementMethod()
    }

    private fun setCalculatorButtons() = with(binding) {
        button0.setOnClickListener { viewModel.addToEquation("0") }
        button1.setOnClickListener { viewModel.addToEquation("1") }
        button2.setOnClickListener { viewModel.addToEquation("2") }
        button3.setOnClickListener { viewModel.addToEquation("3") }
        button4.setOnClickListener { viewModel.addToEquation("4") }
        button5.setOnClickListener { viewModel.addToEquation("5") }
        button6.setOnClickListener { viewModel.addToEquation("6") }
        button7.setOnClickListener { viewModel.addToEquation("7") }
        button8.setOnClickListener { viewModel.addToEquation("8") }
        button9.setOnClickListener { viewModel.addToEquation("9") }
        buttonDot.setOnClickListener { viewModel.addToEquation(".") }
        buttonPlus.setOnClickListener { viewModel.addToEquation(getString(R.string.plus)) }
        buttonMinus.setOnClickListener { viewModel.addToEquation(getString(R.string.minus)) }
        buttonMultiply.setOnClickListener { viewModel.addToEquation(getString(R.string.multiply)) }
        buttonDivide.setOnClickListener { viewModel.addToEquation(getString(R.string.divide)) }
        buttonAc.setOnClickListener { viewModel.clearInput() }
        buttonHistory.setOnClickListener {}

        buttonResult.setOnClickListener {
            viewModel.getResult(textviewResult.text.toString())
        }
    }


    companion object {
        const val TAG = "CalculatorFragment"
        fun newInstance() = CalculatorFragment()
    }


}