package com.example.quizmaster.data.model.OpentdbAPI


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    val category: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    val difficulty: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>,
    val question: String,
    val type: String
): Serializable
