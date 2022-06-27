package com.example.quizmaster.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class UserViewModel: ViewModel() {

    var firebaseAuth: FirebaseAuth

    init {
       firebaseAuth = FirebaseAuth.getInstance()
    }



}