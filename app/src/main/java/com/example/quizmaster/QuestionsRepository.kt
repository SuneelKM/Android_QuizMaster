package com.example.quizmaster

import javax.inject.Inject


class QuestionsRepository @Inject constructor (val inter: RetroApiInterface) {

    fun getQuestions(
        amount: String,
        category: String,
        difficulty: String,
        type: String
    ) = inter.getQuestions(amount, category, difficulty, type)
}