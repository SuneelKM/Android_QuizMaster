package com.example.quizmaster.ui.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class QuestionsTimerViewModel  {
    private var started=false
    private var countDown= MutableLiveData<Int>()
    private var finish= MutableLiveData<String>()

    fun getLiveTimer(): LiveData<Int> {
        return countDown
    }
    fun getFinish(): LiveData<String> {
        return finish
    }


    fun timer(){
        if (!started) {
            started=true
            object : CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val time = millisUntilFinished / 1000
                    countDown.value = time.toInt()
                }
                override fun onFinish() {
                    finish.value = "Time Up"
                }
            }.start()
        }
    }

}