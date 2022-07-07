package com.example.quizmaster.ui.viewmodel

import android.content.Context
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizmaster.R
import com.example.quizmaster.data.model.OpentdbAPI.Result
import com.example.quizmaster.data.model.SubmittedQuestions
import kotlin.collections.ArrayList


class QuizPageViewModel(): ViewModel() {

    private var started=false
    private lateinit var timerObj: CountDownTimer

    private val _timer = MutableLiveData<Int>()
    val timer: LiveData<Int>
        get() = _timer

    val finish= MutableLiveData<String>("Times Up")
    var isFinished = MutableLiveData(false)

    var questionList = ArrayList<Result>()
    var questionIndex = 0

    var questCategory = MutableLiveData<String>()
    var questLevel = MutableLiveData<String>()
    var shuffledAnswers = MutableLiveData<List<String>>()
    var currentQuestion = MutableLiveData<String>()
    var correctAnswer = MutableLiveData<String>()

    var qCategory = ""
    var qLevel = ""
    var correctAnswerText = ""
    var allOptions = ArrayList<TextView>()

    var submittedQuestions = ArrayList<SubmittedQuestions>()

    var score = 0
    var totalScore = 0
    lateinit var context: Context



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
                    timeFinished()
                    started = false
                }
            }.start()
        }
    }

    fun setStopTime (){
        timerObj.cancel()
    }

    fun setQuestions(results: List<Result>, context: Context){
        this.context = context
        questionList.addAll(results)
        loadQuestion(questionIndex)
        timer()
    }
    fun setAllOptions(allOp: List<TextView>){
        allOptions.addAll(allOp)
    }

    fun submitQuestion(selected: TextView){
        val userAnswer = selected.text.toString()

        if(userAnswer.equals(correctAnswerText)){
            selected.background = AppCompatResources.getDrawable(context,R.drawable.correct_option_border_bg)
            score++
        }
        else{
            selected.background = AppCompatResources.getDrawable(context,R.drawable.incorrect_option_border_bg)
            for(op in allOptions){
                if(op.text.toString().equals(correctAnswerText)){
                    op.background = AppCompatResources.getDrawable(context,R.drawable.correct_option_border_bg)
                }
            }

        }
        submittedQuestions.add(SubmittedQuestions(currentQuestion.value.toString(), correctAnswer.value.toString(),userAnswer))
    }

    fun nextQuestion(){
        for(op in allOptions){
            op.background = AppCompatResources.getDrawable(context, R.drawable.default_option_border_bg)
        }
        started = false
        isFinished.postValue(false)
        timer()
        loadQuestion(++questionIndex)
    }

    fun timeFinished(){
        for(op in allOptions){
            val correctAnsColor = "#A5D6A7"
            if(op.text.toString().equals(correctAnswerText)){
                op.background = AppCompatResources.getDrawable(context,R.drawable.correct_option_border_bg)
            }else{
                op.background = AppCompatResources.getDrawable(context, R.drawable.default_option_border_bg)
            }
        }
        submittedQuestions.add(SubmittedQuestions(currentQuestion.value.toString(), correctAnswer.value.toString(),"No answer submitted"))
    }

    fun quizFinished(): Boolean{
        if (questionIndex>= questionList.size-1){
            return true
        }
       return false
    }


    private fun loadQuestion(questionIndex: Int){
        questCategory.postValue(questionList.get(0).category)
        qCategory = questionList.get(0).category
        questLevel.postValue(questionList.get(0).difficulty.replaceFirstChar { it.uppercaseChar() })
        qLevel = questionList.get(0).difficulty.replaceFirstChar { it.uppercaseChar() }

        currentQuestion.postValue(
            HtmlCompat.fromHtml(questionList.get(questionIndex).question,
                HtmlCompat.FROM_HTML_MODE_COMPACT
            ).toString())

        correctAnswerText = HtmlCompat.fromHtml(questionList.get(questionIndex).correctAnswer,
            HtmlCompat.FROM_HTML_MODE_COMPACT
        ).toString()
        correctAnswer.postValue(correctAnswerText)

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

        totalScore++

        shuffledAnswers.postValue(answerArr.shuffled())
    }

}