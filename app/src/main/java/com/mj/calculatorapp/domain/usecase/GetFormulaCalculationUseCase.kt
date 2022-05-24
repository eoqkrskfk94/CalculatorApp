package com.mj.calculatorapp.domain.usecase

import com.mj.calculatorapp.util.provider.DispatcherProvider
import kotlinx.coroutines.withContext
import com.mj.calculatorapp.util.Result
import java.util.*
import javax.inject.Inject

class GetFormulaCalculationUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(formula: String): Result<String> = withContext(dispatcherProvider.default) {
        return@withContext try {
            val result = getResult(infixToPostfix(formula)).trimEnd { it == '0' }.trimEnd { it == '.' }
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun getResult(postfixList: MutableList<String>): String {
        var index = 0
        while (postfixList.size != 1) {
            if (!isNumber(postfixList[index])) {
                val result = calculate(postfixList.removeAt(index - 2), postfixList.removeAt(index - 2), postfixList.removeAt(index - 2))
                postfixList.add(index - 2, result)
                index = -1
            }
            index++
        }
        return postfixList[0]
    }

    private fun infixToPostfix(formula: String): MutableList<String> {
        val infixList = formula.split(" ")
        val postfixList = mutableListOf<String>()
        val operatorStack = Stack<String>()

        for (item in infixList) {
            if (isNumber(item)) {
                postfixList.add(item)
            } else {
                if (operatorStack.isEmpty()) {
                    operatorStack.push(item)
                } else {
                    if (getWeight(operatorStack.peek()) >= getWeight(item)) postfixList.add(operatorStack.pop())
                    operatorStack.push(item)
                }
            }
        }
        while (operatorStack.isNotEmpty()) postfixList.add(operatorStack.pop())

        return postfixList
    }



    private fun calculate(num1: String, num2: String, operator: String): String {
        val firstNumber = num1.toDouble()
        val secondNumber = num2.toDouble()
        var result = 0.0

        try {
            when (operator) {
                "×" -> result = firstNumber * secondNumber
                "÷" -> result = firstNumber / secondNumber
                "+" -> result = firstNumber + secondNumber
                "–" -> result = firstNumber - secondNumber
            }
        } catch (e: Exception) {

        }
        return result.toString()
    }


    private fun getWeight(symbol: String): Int {
        var weight = 0
        when (symbol) {
            "×", "÷" -> weight = 2
            "+", "–" -> weight = 1
        }
        return weight
    }

    private fun isNumber(symbol: String): Boolean {
        return when (symbol.toDoubleOrNull()) {
            null -> false
            else -> true
        }
    }
}