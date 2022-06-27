package com.example.quizmaster.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizmaster.R
import com.example.quizmaster.data.model.OpentdbAPI.Questions
import com.example.quizmaster.data.model.OpentdbAPI.Result

class QuestionPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_page)

        val result = intent.extras?.get("results") as List<Result>
        val pos = intent.extras?.get("position")

        println("From Question Page $result")
        println(pos)

    }
}