package com.example.quizmaster.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.quizmaster.data.model.UserData.UserScores
import com.google.firebase.database.DatabaseReference
import dagger.Lazy
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val context: Context,
    private val myRef: Lazy<DatabaseReference>
) {

    var loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var userScoreList = MutableLiveData<List<UserScores>>()
    var username = MutableLiveData<String>()

    fun getUserName() {
        myRef.get().get()
            .addOnSuccessListener {
            username.postValue(it.child("username").value.toString())
        }
            .addOnFailureListener {
            Toast.makeText(context, "${it.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun getUserScores(){

        val usList = ArrayList<UserScores>()
        loading.postValue(true)
        myRef.get().get()
            .addOnSuccessListener {
                val scores = it.child("scores").children
                for(score in scores){
                    val date = score.child("date").value.toString()
                    val category = score.child("category").value.toString()
                    val level = score.child("level").value.toString()
                    val points = score.child("points").value.toString()
                    val profile = score.child("profile").value.toString()

                    usList.add(UserScores(date,category,level,points,profile))
                    println("Hello      $usList")
                    loading.postValue(false)
                }
                userScoreList.postValue(usList)
            }
            .addOnFailureListener {
                loading.postValue(false)
                Toast.makeText(context, "${it.message}", Toast.LENGTH_LONG).show()
            }

    }
}