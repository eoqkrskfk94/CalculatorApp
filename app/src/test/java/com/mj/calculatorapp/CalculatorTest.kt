package com.mj.calculatorapp

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalculatorTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun infixToPostfix() {
        val sample = listOf("2", "×", "1.5", "+", "6", "÷", "2")
        var postfixList = mutableListOf<String>()
        var operatorStack = Stack<String>()
        for (item in sample) {
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
        println(getResult(postfixList))
    }

    fun getResult(postfixList: MutableList<String>): String {
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

    fun calculate(num1: String, num2: String, operator: String): String {
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


    fun getWeight(symbol: String): Int {
        var weight = 0
        when (symbol) {
            "×", "÷" -> weight = 2
            "+", "–" -> weight = 1
        }
        return weight
    }

    fun isNumber(symbol: String): Boolean {
        return when (symbol.toDoubleOrNull()) {
            null -> false
            else -> true
        }
    }
}