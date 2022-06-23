package com.example.quizmaster.OpentdbAPI


import com.google.gson.annotations.SerializedName

data class Questions(
    @SerializedName("response_code")
    val responseCode: Int,
    val results: List<Result>
)