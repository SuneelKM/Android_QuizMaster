package com.example.quizmaster


import com.example.quizmaster.OpentdbAPI.Questions
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*


interface RetroApiInterface {

    @GET("api.php")
    fun getQuestions(
        @Query("amount") amount: String,
        @Query("category") category: String,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String,
    ): Observable<Questions>



}
