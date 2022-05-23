package com.mj.calculatorapp.presentation.main

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.mj.calculatorapp.R
import com.mj.calculatorapp.databinding.ActivityMainBinding
import com.mj.calculatorapp.presentation.base.BaseActivity
import com.mj.calculatorapp.presentation.main.calculator.CalculatorFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModels()
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()
        showFragment(CalculatorFragment.newInstance(), CalculatorFragment.TAG)
    }

    override fun observeData() { }

    private fun showFragment(fragment: Fragment, tag: String) {

        val findFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction()
                .hide(it)
                .commitAllowingStateLoss()
        }

        findFragment?.let {
            supportFragmentManager.beginTransaction()
                .show(it)
                .commitAllowingStateLoss()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container_view, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

}