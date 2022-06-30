package com.example.quizmaster.ui.view

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.quizmaster.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner ::class)
class SignUpActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<SignUpActivity>()

    @Test
    fun checkingActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.cardView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_Visibility_of_textView_and_button() {
        Espresso.onView(ViewMatchers.withId(R.id.cardView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}