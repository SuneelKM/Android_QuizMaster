package com.example.quizmaster.ui.view

import androidx.core.content.MimeTypeFilter.matches
import androidx.test.espresso.Espresso.onView
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

    @Test
    fun checkingActivity(){
        onView(withId(R.id.constraintLayout)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun test_Visibility_of_textView_and_button() {
        onView(withId(R.id.constraintLayout)).check(ViewAssertions.matches(isDisplayed()))

    }

}