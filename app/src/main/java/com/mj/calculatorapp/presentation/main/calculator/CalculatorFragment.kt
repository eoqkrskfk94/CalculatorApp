package com.mj.calculatorapp.presentation.main.calculator


import androidx.fragment.app.activityViewModels
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
    }

    override fun observeData() {

    }

    companion object {
        const val TAG = "CalculatorFragment"
        fun newInstance() = CalculatorFragment()
    }


}