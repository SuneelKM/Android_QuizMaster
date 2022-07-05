package com.example.quizmaster.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.quizmaster.data.model.UserData.UserSignup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.Lazy
import timber.log.Timber
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val myRef: Lazy<DatabaseReference>

) {
    var signUpState = MutableLiveData<String>()
    var signInState = MutableLiveData<String>()

    fun handleSignUp(userName: String, email: String, pass: String) {
        signUpState.value = "showProgress"
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    sendRegistrationEmail()
                    saveToDatabase(userName, email)
            }
            .addOnFailureListener {
                signUpState.value = "error"
                Toast.makeText(
                    context, "${it.message}", Toast.LENGTH_LONG
                ).show()
            }

    }
    
    
    fun sendRegistrationEmail(){
        try {
            firebaseAuth.currentUser!!.sendEmailVerification()
        }catch (e:Exception){
            signUpState.value = "error"
            Toast.makeText(
                context, "Something went wrong", Toast.LENGTH_LONG
            ).show()
        }
    }

    fun saveToDatabase(userName: String, email: String) {
        val user = UserSignup(userName, email, "")
        try {
//            val database = Firebase.database
//            val uid = firebaseAuth.uid
//            val myRef = database.getReference("/Users/$uid")
            myRef.get().setValue(user)
//            myRef.setValue(user)
            signUpState.value = "success"
            Toast.makeText(
                context, "Registration successful. Check you email.", Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {
            signUpState.value = "error"
            Toast.makeText(
                context, "Something went wrong", Toast.LENGTH_LONG
            ).show()
        }
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
                            context,
                            "Please verify your email address",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }.addOnFailureListener {
                signInState.value = "hideProgress"
                Toast.makeText(context, "${it.message}", Toast.LENGTH_LONG).show()
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

    fun handleResetPassword(email: String) {
        signInState.value = "showProgress"
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
                signInState.value = "error"
                Toast.makeText(context, "${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun sendPassResetEmail(email: String) {
        try {
            firebaseAuth.sendPasswordResetEmail(email)
            signInState.value = "email sent"
        } catch (e: Exception) {
            Timber.e(e)
            signInState.value = "error"
        }
    }

    fun handleSignOut(){
        firebaseAuth.signOut()
    }
}
