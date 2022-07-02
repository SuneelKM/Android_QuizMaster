package com.example.quizmaster.data.model

data class SubmittedQuestions(
    val question: String,
    val correctAnswer: String,
    val userAnswer: String
)