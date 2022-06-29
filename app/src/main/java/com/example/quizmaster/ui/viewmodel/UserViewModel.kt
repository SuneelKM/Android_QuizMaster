package com.example.quizmaster.ui.viewmodel

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizmaster.R
import com.example.quizmaster.data.model.UserData.UserScores
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class UserViewModel: ViewModel() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = Firebase.database
    val uid = firebaseAuth.uid
    val myRef = database.getReference("/Users/$uid")

    var loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var userScoreList = MutableLiveData<List<UserScores>>()
    var username = MutableLiveData<String>()

    init {
        loading.postValue(false)
        getUserName()
    }


    private fun getUserName(){
        myRef.get().addOnSuccessListener {
            username.postValue(it.child("username").value.toString())
        }
    }


    fun getUserScores(context: Context){

        var usList = ArrayList<UserScores>()
        loading.postValue(true)
        myRef.get()
            .addOnSuccessListener {

                val scores = it.child("scores").children
                for(score in scores){
                    val date = score.child("date").value.toString()
                    val category = score.child("category").value.toString()
                    val level = score.child("level").value.toString()
                    val points = score.child("points").value.toString()
                    val profile = score.child("profile").value.toString()

                    usList.add(UserScores(date,category,level,points,profile))
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