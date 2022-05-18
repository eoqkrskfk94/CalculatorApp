package com.mj.calculatorapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mj.calculatorapp.R
import com.mj.calculatorapp.databinding.ActivityMainBinding
import com.mj.calculatorapp.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModels()
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()
    }

    override fun observeData() {

    }

}