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
class QuestionPageActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<QuestionPageActivity>()


    // layout is displayed to the user
    @Test
    fun checkingActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.QuestionPage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    // to check Main activity layout is displayed to the user
//    @Test
//    fun checkActivityVisibility() {
//        Espresso.onView(ViewMatchers.withId(R.id.textView))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//    }
//
//    // to check if text " Main Activity is visible as we want"
////    @Test
////    fun checkingTextVisibility() {
////        Espresso.onView(ViewMatchers.withId(R.id.cardView))
////            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
////
////        Espresso.onView(ViewMatchers.withId(R.id.cardView))
////            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
////    }
}