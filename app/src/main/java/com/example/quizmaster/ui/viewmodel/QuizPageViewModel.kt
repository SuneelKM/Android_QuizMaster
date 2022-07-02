package com.example.quizmaster.ui.viewmodel

import android.graphics.Color
import android.os.CountDownTimer
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizmaster.R
import com.example.quizmaster.data.model.OpentdbAPI.Result
import com.example.quizmaster.data.model.SubmittedQuestions

class QuizPageViewModel: ViewModel() {

    private var started=false
    private lateinit var timerObj: CountDownTimer

    private val _timer = MutableLiveData<Int>()
    val timer: LiveData<Int>
        get() = _timer

    var isFinished = MutableLiveData(false)

    var questionList = ArrayList<Result>()
    var questionIndex = 0

    var questCategory = MutableLiveData<String>()
    var questLevel = MutableLiveData<String>()
    var shuffledAnswers = MutableLiveData<List<String>>()
    var currentQuestion = MutableLiveData<String>()
    var correctAnswer = MutableLiveData<String>()

    var submittedQuestions = ArrayList<SubmittedQuestions>()

    var score = 0

    private val _finish= MutableLiveData<String>("Times Up")
    val finish: LiveData<String>
        get() = _finish

    //var countDown = 30



    fun timer(){
        if (!started) {
            started=true
            timerObj = object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val time = millisUntilFinished / 1000
                    _timer.value = time.toInt()
                }
                override fun onFinish() {
                    isFinished.postValue(true)
                    started = false
                }
            }.start()
        }
    }

    fun setStopTime (){
        timerObj.cancel()
    }

    fun setQuestions(results: List<Result>){
        questionList.addAll(results)
        loadQuestion(questionIndex)
    }

    fun submitQuestion(selected: TextView){
        val correctAnsColor = "#EF9A9A"
        val incorrectAnsColor = "#A5D6A7"
        val userAnswer = selected.text.toString()
        if(userAnswer.equals(correctAnswer)){
            selected.setBackgroundColor(Color.parseColor(correctAnsColor))
        }
        else{
            selected.setBackgroundColor(Color.parseColor(incorrectAnsColor))

        }


        submittedQuestions.add(SubmittedQuestions(currentQuestion.value.toString(), correctAnswer.value.toString(),userAnswer))
    }

    fun loadQuestion(questionIndex: Int){
        questCategory.postValue(questionList.get(0).category)
        questLevel.postValue(questionList.get(0).difficulty.replaceFirstChar {
            it.uppercaseChar()
        })
        currentQuestion.postValue(
            HtmlCompat.fromHtml(questionList.get(questionIndex).question,
                HtmlCompat.FROM_HTML_MODE_COMPACT
            ).toString())
        correctAnswer.postValue(
            HtmlCompat.fromHtml(questionList.get(questionIndex).correctAnswer,
                HtmlCompat.FROM_HTML_MODE_COMPACT
            ).toString())

        var answerArr = listOf(
            HtmlCompat.fromHtml(questionList.get(questionIndex).correctAnswer,
                HtmlCompat.FROM_HTML_MODE_COMPACT
            ).toString(),
            HtmlCompat.fromHtml(questionList.get(questionIndex).incorrectAnswers.get(0),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            ).toString(),
            HtmlCompat.fromHtml(questionList.get(questionIndex).incorrectAnswers.get(1),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            ).toString(),
            HtmlCompat.fromHtml(questionList.get(questionIndex).incorrectAnswers.get(2),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            ).toString()
        )

        shuffledAnswers.postValue(answerArr.shuffled())

        println("From Loaded Question: ${currentQuestion.value.toString()}")

    }

}