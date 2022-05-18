package com.mj.calculatorapp.presentation.main.history


import androidx.fragment.app.activityViewModels
import com.mj.calculatorapp.databinding.FragmentHistoryBinding
import com.mj.calculatorapp.presentation.base.BaseFragment
import com.mj.calculatorapp.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryFragment : BaseFragment<MainViewModel, FragmentHistoryBinding>() {

    override val viewModel: MainViewModel by activityViewModels()
    override fun getViewBinding(): FragmentHistoryBinding = FragmentHistoryBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()
    }

    override fun observeData() {

    }

    companion object {
        const val TAG = "HistoryFragment"
        fun newInstance() = HistoryFragment()
    }


}