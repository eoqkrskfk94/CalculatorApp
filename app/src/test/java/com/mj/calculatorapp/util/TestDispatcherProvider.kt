package com.mj.calculatorapp.util

import com.mj.calculatorapp.util.provider.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher

class TestDispatcherProvider(
    private val testDispatcher: TestDispatcher
) : DispatcherProvider {

    override val default: CoroutineDispatcher
        get() = testDispatcher
    override val io: CoroutineDispatcher
        get() = testDispatcher
    override val main: CoroutineDispatcher
        get() = testDispatcher


}