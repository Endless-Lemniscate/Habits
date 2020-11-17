package com.example.habits

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Test
    fun is_activity_in_view() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.main))
            .check(matches(isDisplayed()))
    }

    @Test
    fun is_list_fragment_visible() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.list_fragment))
            .check(matches(isDisplayed()))
    }

    @Test
    fun is_bottom_sheet_fragment_visible() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.bottom_sheet_fragment))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_navigation_to_details_fragment() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.fragment_habit_details))
            .check(matches(isDisplayed()))
    }
}