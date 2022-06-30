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
class SplashActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<SplashActivity>()

    // layout is displayed to the user
    @Test
    fun checkingActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.Splash))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

//    text  is visible as we want
    @Test
    fun checkingTextVisibility () {
        Espresso.onView(ViewMatchers.withId(R.id.Splash))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.Splash))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }











}