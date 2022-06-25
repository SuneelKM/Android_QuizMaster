package com.example.quizmaster.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.quizmaster.data.model.OpentdbAPI.Questions
import com.example.quizmaster.data.repo.QuestionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor (val repo: QuestionsRepository) : ViewModel() {

    fun getQuestions(
        amount: String,
        category: String,
        difficulty: String,
        type: String
    ): Observable<Questions> {
        return repo.getQuestions(amount, category, difficulty, type)
    }
}