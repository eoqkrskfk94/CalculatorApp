package com.mj.calculatorapp.util

import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


class ToastMatcher(message: String) : TypeSafeMatcher<Root?>() {

    private val message: String

    override fun matchesSafely(root: Root?): Boolean {
        val type = root?.windowLayoutParams?.get()?.type
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken = root.decorView.windowToken
            val appToken = root.decorView.applicationWindowToken
            if (windowToken === appToken) {
                // means this window isn't contained by any other windows.
                return true
            }
        }
        return false
    }

    override fun describeTo(description: Description) {
        description.appendText(message)
    }

    init {
        this.message = message
    }
}