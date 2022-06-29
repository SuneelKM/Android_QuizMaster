package com.example.quizmaster.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizmaster.R
import com.example.quizmaster.data.model.OpentdbAPI.Result

class ResultPageActivity : AppCompatActivity() {
    var score = 0
    lateinit var result:List<Result>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)

//        result = (intent.extras?.get("results") as? List<Result>)!!
//        score = intent.extras?.get("score") as Int
//        println("ResultPageActivity  $score")
//        println("ResultPageActivity  $result")

        println("ResultPageActivity")
    }
}