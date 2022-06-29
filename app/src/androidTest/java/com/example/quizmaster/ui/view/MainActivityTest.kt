package com.example.quizmaster

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.quizmaster.ui.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner ::class)
class MainActivityTest {

    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()


    // to check Main activity layout is displayed to the user
    @Test
    fun checkActivityVisibility() {
        onView(withId(R.id.drawer)).check(matches(isDisplayed()))
    }

    // to check if text " Main Activity is visible as we want"
    @Test
    fun checkingTextVisibility () {
        onView(withId(R.id.textView4)).check(matches(isDisplayed()))

        onView(withId(R.id.quiz_setup_button)).check(matches(isDisplayed()))
    }



    // testing click in button in Main Activity if we click it goes to second activity
    // second activity will be displayed

    @Test
    fun navigateTo2Activity() {
        onView(withId(R.id.quiz_setup_button)).perform(click())
        onView(withId(R.id.history_setup_button)).perform(click()) }







}