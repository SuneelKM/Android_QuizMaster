package com.example.quizmaster

class QuestionsRepository(val inter: RetroApiInterface) {

    fun getQuestions(
        amount: String,
        category: String,
        difficulty: String,
        type: String
    ) = inter.getQuestions(amount, category, difficulty, type)
}