package com.example.quizmaster.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (var repo: AuthRepository) : ViewModel() {

    var signUpState: MutableLiveData<String> = repo.signUpState
    var signInState: MutableLiveData<String> = repo.signInState

    fun handleSignUp(userName: String, email: String, pass: String) {
        repo.handleSignUp(userName, email, pass)
    }

    fun handleSignIn(email: String, pass: String) {
        repo.handleSignIn(email, pass)
    }

    fun isEmailVerified(): Boolean {
        return repo.isEmailVerified()
    }

    fun handleResetPassword(email: String) {
        repo.handleResetPassword(email)
    }

}


