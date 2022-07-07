package com.example.quizmaster.ui.viewmodel

import android.content.Context
import android.content.res.Configuration
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizmaster.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import java.util.regex.Pattern
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

    fun handleSignOut() {
        repo.handleSignOut()
    }

    fun isValidPassword(password: String) : Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=_.])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$")
        return passwordREGEX.matcher(password).matches()
    }

    fun setLanguage(baseContext: Context) {
        var l = ""
        val config: Configuration = baseContext.resources.configuration
        val localLang = config.locales[0].toString()
        if(localLang == "en_us" || localLang == "en_US")
            l = "hi"
        else if(localLang == "hi")
            l = "en_us"
        val locale = Locale(l)
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}


