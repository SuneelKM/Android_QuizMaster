package com.example.quizmaster.ui.view

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.quizmaster.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner ::class)
class HistoryActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<HistoryActivity>()


    // layout is displayed to the user
    @Test
    fun checkingActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.HistoryPage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_Visibility_of_textView_and_button() {
        Espresso.onView(ViewMatchers.withId(R.id.HistoryPage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }


    // to check layout is displayed to the user
    @Test
    fun checkActivityVisibility() {
        Espresso.onView(ViewMatchers.withId(R.id.HistoryPage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    // to check if text "  is visible as we want"
    @Test
    fun checkingTextVisibility () {
        Espresso.onView(ViewMatchers.withId(R.id.HistoryPage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.HistoryPage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    // second activity will be displayed

    @Test
    fun navigateTo2Activity() {
        Espresso.onView(ViewMatchers.withId(R.id.HistoryPage)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.HistoryPage)).perform(ViewActions.click()) }




}