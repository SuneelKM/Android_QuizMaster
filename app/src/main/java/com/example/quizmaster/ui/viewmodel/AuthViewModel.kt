package com.example.quizmaster.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizmaster.data.model.UserData.UserSignup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class AuthViewModel(app: Application) : AndroidViewModel(app) {

    var signUpState = MutableLiveData<String>()
    var signInState = MutableLiveData<String>()
    var firebaseAuth = FirebaseAuth.getInstance()


    fun handleSignUp(userName: String, email: String, pass: String) {
        signUpState.value = "showProgress"
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    saveToDatabase(userName, email)
            }
            .addOnFailureListener {
                signUpState.value = "error"
                Toast.makeText(
                    this.getApplication(), "${it.message}", Toast.LENGTH_LONG
                ).show()
            }
    }

    fun saveToDatabase(userName: String, email: String) {
        val user = UserSignup(userName, email)
        val database = Firebase.database
        val uid = firebaseAuth.uid
        val myRef = database.getReference("/Users/$uid")
        myRef.setValue(user)
        signUpState.value = "success"
    }


    fun handleSignIn(email: String, pass: String) {
        signInState.value = "showProgress"
        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (isEmailVerified()) {
                        signInState.value = "success"
                    } else {
                        signInState.value = "hideProgress"
                        Toast.makeText(
                            this.getApplication(),
                            "Please verify your email address",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }.addOnFailureListener {
                signInState.value = "hideProgress"
                Toast.makeText(this.getApplication(), "${it.message}", Toast.LENGTH_LONG).show()
            }
    }


    fun isEmailVerified(): Boolean {
        var emailVerified = false
        try {
            if (firebaseAuth.currentUser != null)
            emailVerified = firebaseAuth.currentUser!!.isEmailVerified
        } catch (e: Exception) {
            Timber.e(e)
            signInState.value = "error"
        }
        return emailVerified
    }

    fun handleResetPassword(email: String){
        signInState.value = "loading"
        firebaseAuth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result?.signInMethods?.size != 0) {
                        sendPassResetEmail(email)
                    } else
                        signInState.value = "not found"
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    this.getApplication(),
                    "${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        signInState.value = "loaded"
    }

    private fun sendPassResetEmail(email:String){
        try {
            firebaseAuth.sendPasswordResetEmail(email)
            signInState.value = "email sent"
        } catch (e:Exception){
            Timber.e(e)
            signInState.value = "error"
        }
    }


}