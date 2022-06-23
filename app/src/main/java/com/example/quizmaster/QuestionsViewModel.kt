package com.example.quizmaster

import androidx.lifecycle.ViewModel
import com.example.quizmaster.OpentdbAPI.Questions
import io.reactivex.rxjava3.core.Observable

class QuestionsViewModel(val repo: QuestionsRepository) : ViewModel() {

    fun getQuestions(
        amount: String,
        category: String,
        difficulty: String,
        type: String
    ): Observable<Questions> {
        return repo.getQuestions(amount, category, difficulty, type)
    }
}