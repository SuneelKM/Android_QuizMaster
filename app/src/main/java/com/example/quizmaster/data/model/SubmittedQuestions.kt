package com.example.quizmaster.data.model

import java.io.Serializable

data class SubmittedQuestions(
    val question: String,
    val correctAnswer: String,
    val userAnswer: String
): Serializable