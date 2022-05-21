package com.mj.calculatorapp.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@ExperimentalCoroutinesApi
class MainCoroutineExtension(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : BeforeEachCallback, AfterEachCallback {

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        return testDispatcher.runBlockingTest(block)
    }

}