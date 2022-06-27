package com.example.quizmaster.ui.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.quizmaster.R
import com.example.quizmaster.data.api.RetroApiInterface
import com.example.quizmaster.data.repo.QuestionsRepository
import com.example.quizmaster.ui.viewmodel.QuestionsTimerViewModel
import com.example.quizmaster.ui.viewmodel.QuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import com.example.quizmaster.data.model.OpentdbAPI.Result
import java.lang.Math.random


@AndroidEntryPoint
class QuestionPageActivity : AppCompatActivity() {
   lateinit var vmT: QuestionsTimerViewModel
    val vm: QuestionsViewModel by viewModels()

    lateinit var textView2: TextView
    lateinit var textView3: TextView
    lateinit var textView4: TextView
    lateinit var textView5: TextView
    lateinit var textView6: TextView
    lateinit var textView7: TextView
    lateinit var textView31: TextView
    lateinit var timer: TextView
    lateinit var button2: Button
     var correctAns = String()
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_page)

        vmT = QuestionsTimerViewModel()
        timer = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        textView31 = findViewById(R.id.textView31)
        textView4 = findViewById(R.id.textView4)
        textView5 = findViewById(R.id.textView5)
        textView6 = findViewById(R.id.textView6)
        textView7 = findViewById(R.id.textView7)
        button2 = findViewById(R.id.button2)

        vmT.timer()
        vmT.getLiveTimer().observe(this, { timer.text = "Time Left ${it.toString()}" })
        vmT.getFinish().observe(this, { timer.text = it })

//        vm.getQuestions("2","9","medium", "multiple")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onNext ={
//                    println("results  ${it.results}")
//                    for (i in it.results){
//                        println("cat ${it.results[1].question}")
//                    }
//                  //  println("results[4]  ${it.results}")
//
//     //               textView2.text=it.results[4].toString()
////                  Html.fromHtml(it.results[0].question)
//                },
//                onError = {e -> println("this is error $e")}
//            )

        val result = intent.extras?.get("results") as? List<Result>
        val pos = intent.extras?.get("position")
        if (result != null) {
            var i =0
         // while ((timer.text.toString().toInt()!=0)) {
                 for (i in 0..0 ) {
                     println("From Question Page ${result?.get(1)?.question}")
                     println("timer in wile loop")

                     //       button2.setOnClickListener {
                     textView2.text = result?.get(i)?.question.toString()
                     textView3.text = result?.get(i)?.category.toString()
                     textView31.text = "Level:  ${result?.get(i)?.difficulty.toString()}"
                     correctAns = result?.get(i)?.correctAnswer.toString()

                     var answerArr = listOf(
                         result?.get(i)?.correctAnswer.toString(),
                         result?.get(i)?.incorrectAnswers?.get(0).toString(),
                         result?.get(i)?.incorrectAnswers?.get(1).toString(),
                         result?.get(i)?.incorrectAnswers?.get(2).toString()
                     )
                     println("answerArr  ${answerArr.get(0).toString()}")

                     var randomAnswerArr = answerArr.shuffled()
                     println("randomAnswerArr $randomAnswerArr")

                     textView4.text = randomAnswerArr.get(0).toString()
                     textView5.text = randomAnswerArr.get(1).toString()
                     textView6.text = randomAnswerArr.get(2).toString()
                     textView7.text = randomAnswerArr.get(3).toString()

//        textView4.text=result?.get(1)?.correctAnswer .toString()
//        textView5.text=result?.get(1)?.incorrectAnswers?.get(0) .toString()
//        textView6.text=result?.get(1)?.incorrectAnswers?.get(1).toString()
//        textView7.text=result?.get(1)?.incorrectAnswers?.get(2).toString()

                     println(pos)
                     textView4.setOnClickListener(object : View.OnClickListener {
                         override fun onClick(v: View?) {
                             if (v != null) {
                                 if (textView4.text.toString() == correctAns && textView4.id == v.id) {
                                     println("correctAns ${textView4.text.toString()}")
                                     textView4.setBackgroundColor(Color.parseColor("#00FF00"))
                                 } else {
                                     println("IncorrectAns ${textView4.text.toString()}")
                                     textView4.setBackgroundColor(
                                         android.graphics.Color.parseColor("#ff3300"))
                                 }
                             }
                         }
                     })
                     textView5.setOnClickListener(object : View.OnClickListener {
                         override fun onClick(v: View?) {
                             if (v != null) {
                                 if (textView5.text.toString() == correctAns && textView5.id == v.id) {
                                     println("correctAns ${textView5.text.toString()}")
                                     textView5.setBackgroundColor(Color.parseColor("#00FF00"))
                                 } else {
                                     println("IncorrectAns ${textView5.text.toString()}")
                                     textView5.setBackgroundColor(
                                         android.graphics.Color.parseColor("#ff3300"))
                                 }
                             }
                         }
                     })

                     textView6.setOnClickListener(object : View.OnClickListener {
                         override fun onClick(v: View?) {
                             if (v != null) {
                                 if (textView6.text.toString() == correctAns && textView6.id == v.id) {
                                     println("correctAns ${textView6.text.toString()}")
                                     textView6.setBackgroundColor(Color.parseColor("#00FF00"))
                                 } else {
                                     println("IncorrectAns ${textView6.text.toString()}")
                                     textView6.setBackgroundColor(
                                         android.graphics.Color.parseColor(
                                             "#ff3300"
                                         )
                                     )
                                 }
                             }
                         }
                     })
                     textView7.setOnClickListener(object : View.OnClickListener {
                         override fun onClick(v: View?) {
                             if (v != null) {
                                 if (textView7.text.toString() == correctAns && textView7.id == v.id) {
                                     println("correctAns ${textView7.text.toString()}")
                                     textView7.setBackgroundColor(Color.parseColor("#00FF00"))
                                 } else {
                                     println("IncorrectAns ${textView7.text.toString()}")
                                     textView7.setBackgroundColor(
                                         android.graphics.Color.parseColor(
                                             "#ff3300"
                                         )
                                     )
                                 }
                             }
                         }
                     })
                     //         i++
                 //}
                 }
            //}

        }


    }


}