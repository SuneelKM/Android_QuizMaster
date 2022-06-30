package com.example.quizmaster.ui.view

import androidx.core.content.MimeTypeFilter.matches
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.quizmaster.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner ::class)
class SignInActivityTest {

    @get:Rule var activityScenarioRule = activityScenarioRule<SignInActivity>()

    // layout is displayed to the user
    @Test
    fun checkingActivity(){
        onView(withId(R.id.constraintLayout)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun test_Visibility_of_textView_and_button() {
        onView(withId(R.id.constraintLayout)).check(ViewAssertions.matches(isDisplayed()))

    }
    //    text  is visible as we want
    @Test
    fun checkingTextVisibility () {
        onView(withId(R.id.constraintLayout)).check(ViewAssertions.matches(isDisplayed()))
    }



    // testing click in button in Main Activity if we click it goes to second activity
    // second activity will be displayed

    @Test
    fun navigateTo2Activity() {
        onView(withId(R.id.constraintLayout)).perform(ViewActions.click())
        onView(withId(R.id.constraintLayout)).perform(ViewActions.click()) }


}