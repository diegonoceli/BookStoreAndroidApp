package com.noceli.diego.androidbookstore

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testScrollAndClickItem() {
        Espresso.onView(ViewMatchers.withId(R.id.itemListView))
            .perform(ViewActions.swipeUp())

        Espresso.onView(ViewMatchers.withId(R.id.itemListView))
            .perform(ViewActions.click())

    }

    @Test
    fun testToggleFilter() {
        Espresso.onView(ViewMatchers.withId(R.id.filterIcon))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.itemListView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.filterIcon))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.itemListView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
