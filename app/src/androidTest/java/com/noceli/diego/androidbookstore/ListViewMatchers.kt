package com.noceli.diego.androidbookstore

import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object ListViewMatchers {

    fun withListSize(sizeMatcher: Matcher<Int>): Matcher<View> {
        return object : BoundedMatcher<View, AdapterView<*>>(AdapterView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("with list size: ")
                sizeMatcher.describeTo(description)
            }

            override fun matchesSafely(view: AdapterView<*>): Boolean {
                val adapter = view.adapter
                return adapter?.count?.let { sizeMatcher.matches(it) } ?: false
            }
        }
    }

    fun withListSize(size: Int): Matcher<View> {
        return withListSize(org.hamcrest.Matchers.`is`(size))
    }
}
