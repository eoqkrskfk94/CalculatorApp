package com.mj.calculatorapp.presentation.main.calculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mj.calculatorapp.R
import com.mj.calculatorapp.presentation.main.MainActivity
import com.mj.calculatorapp.presentation.main.calculator.adapter.HistoryListAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CalculatorFragmentTest {


    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun insertMathExpressionTest() {
        val expectedAnswer = "2 × 1.5 + 6 ÷ 2"

        onView(withId(R.id.button_ac)).perform(click())

        onView(withId(R.id.button_2)).perform(click())
        onView(withId(R.id.button_multiply)).perform(click())
        onView(withId(R.id.button_1)).perform(click())
        onView(withId(R.id.button_dot)).perform(click())
        onView(withId(R.id.button_5)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_6)).perform(click())
        onView(withId(R.id.button_divide)).perform(click())
        onView(withId(R.id.button_2)).perform(click())

        onView(withId(R.id.textview_result)).check(matches(withText(expectedAnswer)))
    }

    @Test
    fun calculateValidMathExpressionTest() {
        val expectedAnswer = "6"

        onView(withId(R.id.button_ac)).perform(click())

        onView(withId(R.id.button_2)).perform(click())
        onView(withId(R.id.button_multiply)).perform(click())
        onView(withId(R.id.button_1)).perform(click())
        onView(withId(R.id.button_dot)).perform(click())
        onView(withId(R.id.button_5)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_6)).perform(click())
        onView(withId(R.id.button_divide)).perform(click())
        onView(withId(R.id.button_2)).perform(click())

        onView(withId(R.id.button_result)).perform(click())

        onView(withId(R.id.textview_result)).check(matches(withText(expectedAnswer)))
    }

    @Test
    fun acClearButtonTest() {
        val expectedAnswer = ""

        onView(withId(R.id.button_ac)).perform(click())

        onView(withId(R.id.button_2)).perform(click())
        onView(withId(R.id.button_multiply)).perform(click())
        onView(withId(R.id.button_1)).perform(click())

        onView(withId(R.id.button_ac)).perform(click())

        onView(withId(R.id.textview_result)).check(matches(withText(expectedAnswer)))
    }


//    @Test
//    fun calculateInValidMathExpressionTest() {
//        onView(withId(R.id.button_2)).perform(click())
//        onView(withId(R.id.button_multiply)).perform(click())
//        onView(withId(R.id.button_multiply)).perform(click())
//        onView(withId(R.id.button_1)).perform(click())
//
//        onView(withId(R.id.button_result)).perform(click())
//
//
//
//        onView(withText("연산하는데 오류가 발생했습니다. 초기화 후 다시 입력해주세요"))
//            .inRoot(ToastMatcher("연산하는데 오류가 발생했습니다. 초기화 후 다시 입력해주세요"))
//            .check(matches(isDisplayed()))
//    }


    @Test
    fun historyShowUpTest() {
        onView(withId(R.id.button_history)).perform(click())
        onView(withId(R.id.imageview_delete)).perform(click())
        onView(withId(R.id.constraintlayout_history)).check(matches(isDisplayed()))
    }

    @Test
    fun historyListClickTest() {
        val expectedAnswer = "2 × 1.5 + 6 ÷ 2"

        onView(withId(R.id.button_ac)).perform(click())

        onView(withId(R.id.button_history)).perform(click())
        onView(withId(R.id.imageview_delete)).perform(click())
        onView(withId(R.id.imageview_close)).perform(click())

        onView(withId(R.id.button_2)).perform(click())
        onView(withId(R.id.button_multiply)).perform(click())
        onView(withId(R.id.button_1)).perform(click())
        onView(withId(R.id.button_dot)).perform(click())
        onView(withId(R.id.button_5)).perform(click())
        onView(withId(R.id.button_plus)).perform(click())
        onView(withId(R.id.button_6)).perform(click())
        onView(withId(R.id.button_divide)).perform(click())
        onView(withId(R.id.button_2)).perform(click())

        onView(withId(R.id.button_result)).perform(click())

        onView(withId(R.id.button_history)).perform(click())
        onView(withId(R.id.recyclerview_history))
            .perform(actionOnItemAtPosition<HistoryListAdapter.ListViewHolder>(0, click()))

        onView(withId(R.id.textview_result)).check(matches(withText(expectedAnswer)))
    }
}
