package com.example.quizmaster.ui.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.example.quizmaster.R
import com.example.quizmaster.ui.viewmodel.QuestionsTimerViewModel
import com.example.quizmaster.ui.viewmodel.QuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.quizmaster.data.model.OpentdbAPI.Result
import java.io.Serializable




@AndroidEntryPoint
class QuestionPageActivity : AppCompatActivity(),View.OnClickListener {
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

    lateinit var submit_btn: TextView
    lateinit var next_btn: TextView
    lateinit var button: Button

    lateinit var button2: Button

    var correctAns = String()
    var score = 0
    private var currentQuestionIndex = 0
    private var selectedAnswer:String = ""
    lateinit var result:List<Result>
    lateinit var questionPageIntent:Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_page)


        vmT = QuestionsTimerViewModel()
        timer = findViewById(R.id.timer)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        textView31 = findViewById(R.id.textView31)
        textView4 = findViewById(R.id.textView4)
        textView5 = findViewById(R.id.textView5)
        textView6 = findViewById(R.id.textView6)
        textView7 = findViewById(R.id.textView7)
        submit_btn = findViewById(R.id.submit_btn)
        next_btn = findViewById(R.id.next_btn)
        button = findViewById(R.id.stopButton)
        questionPageIntent = Intent()
        textView4.setOnClickListener(this)
        textView5.setOnClickListener(this)
        textView6.setOnClickListener(this)
        textView7.setOnClickListener(this)
        submit_btn.setOnClickListener(this)
        next_btn.setOnClickListener(this)

        vmT.timer()
        vmT.getLiveTimer().observe(this, { timer.text = "Time Left ${it.toString()}" })
        vmT.getFinish().observe(this, { timer.text = it })

         result = (intent.extras?.get("results") as? List<Result>)!!
        println("result.size ${result.size}")
        button.setOnClickListener {
            var mainActivityIntent = Intent(this, MainActivity::class.java)
           startActivity(mainActivityIntent)

        }


        submit_btn.setOnClickListener {
            if (selectedAnswer == correctAns) {
         //       this.score = intent.getIntExtra("score", 0)
                score += 1
                println("selectedAnswer == correctAns ${selectedAnswer == correctAns}")
                println("score++  ${score}")
             println("intent   ${questionPageIntent.putExtra("score", score)}")

            }
            submit_btn.setBackgroundColor(Color.parseColor("#cccccc"))
        }
        var questPageIntent = Intent(this, QuestionPageActivity::class.java)
        questPageIntent.putExtra("score", score)

        if (result != null) {
//            println("From Question Page ${result?.get(1)?.question}")
            textView3.text = result?.get(0)?.category.toString()
            textView31.text = "Level:  ${result?.get(0)?.difficulty.toString()}"
            correctAns = result?.get(currentQuestionIndex)?.correctAnswer.toString()
        loadQuestion(result)
        }

    }  // onCreate ends

    override fun onClick(v: View?) {
        var clickedNextBtn : TextView = v as TextView
        var clickedOption: TextView = v as TextView

        textView4.setBackgroundColor(Color.WHITE)
        textView5.setBackgroundColor(Color.WHITE)
        textView6.setBackgroundColor(Color.WHITE)
        textView7.setBackgroundColor(Color.WHITE)
        submit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))

        if (clickedNextBtn != null) {
            println("next btn")
            if (clickedNextBtn.id == R.id.next_btn) {
                if (currentQuestionIndex <= result.size - 1) {
                    currentQuestionIndex++

                    questionPageIntent = Intent(this, QuestionPageActivity::class.java)
                    questionPageIntent.putExtra("results", result as Serializable)
                    questionPageIntent.putExtra("currentQuestionIndex", currentQuestionIndex)
                    questionPageIntent.putExtra("score", score)
                    startActivity(questionPageIntent)

                }else if (currentQuestionIndex == result.size - 1) {
                    next_btn.setText("Finish")
                }
            }
        }
        if (clickedOption != null) {
            if (clickedOption.id == R.id.textView4 || clickedOption.id == R.id.textView5 ||
                clickedOption.id == R.id.textView6 || clickedOption.id == R.id.textView7) {
                selectedAnswer = clickedOption.text.toString()
                println("selectedAnswer $selectedAnswer")
                clickedOption.setBackgroundColor(Color.parseColor("#cc99ff"))
            }
        }
    }

    fun loadQuestion(result: List<Result>?){

        this.currentQuestionIndex = intent.getIntExtra("currentQuestionIndex",0)
        this.score = intent.getIntExtra("score", 0)
        println("this.score ${this.score}")
        println("this.currentQuestionIndex  ${this.currentQuestionIndex}")
        if (result != null) {
            if(this.currentQuestionIndex == result.size){
             finishQuiz()
             return
            }
             }
        selectedAnswer=" "

        textView2.text = result?.get(this.currentQuestionIndex)?.question.toString()
        var answerArr = listOf(
            result?.get(this.currentQuestionIndex)?.correctAnswer.toString(),
            result?.get(this.currentQuestionIndex)?.incorrectAnswers?.get(0).toString(),
            result?.get(this.currentQuestionIndex)?.incorrectAnswers?.get(1).toString(),
            result?.get(this.currentQuestionIndex)?.incorrectAnswers?.get(2).toString()
        )
        var randomAnswerArr = answerArr.shuffled()

        textView4.text = randomAnswerArr.get(0)
        textView5.text = randomAnswerArr.get(1)
        textView6.text = randomAnswerArr.get(2)
        textView7.text = randomAnswerArr.get(3)

    }


    fun finishQuiz(){
        var resultPageIntent = Intent(this, ResultPageActivity::class.java)
//        resultPageIntent.putExtra("scores", score)
        resultPageIntent.putExtra("results", result as Serializable)
        startActivity(resultPageIntent)

    }

}
