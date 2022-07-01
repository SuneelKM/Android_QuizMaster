package com.example.quizmaster.ui.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.quizmaster.data.model.UserData.UserSignup
import com.example.quizmaster.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.quizmaster.R
import com.example.quizmaster.ui.viewmodel.AuthViewModel
import com.example.quizmaster.ui.viewmodel.QuestionsViewModel


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    val vm: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInText.setOnClickListener {
            onBackPressed()
            overridePendingTransition(R.anim.try1, R.anim.try2)
        }

        binding.signUpBtn.setOnClickListener {
            val userName = binding.userEt.text.toString()
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()
            val termsCondition = binding.checkBox.isChecked

            if (userName.isEmpty()) {
                binding.userEt.error = "Please enter a username"
            } else if (email.isEmpty())
                binding.emailEt.error = "Please enter a valid email"
            else if (pass.isEmpty())
                binding.passET.error = "Please enter a valid password"
            else if (confirmPass.isEmpty())
                binding.confirmPassEt.error = "Please enter a valid password"
            else if (!termsCondition)
                binding.checkBox.error = "Please accept the terms and conditions"
            else {
                if (pass == confirmPass) {
                    vm.handleSignUp(userName, email, pass)
                } else {
                    Toast.makeText(this, "Passwords does not match", Toast.LENGTH_SHORT).show()
                }
            }
        }

        vm.signUpState.observe(this) {
            when (it) {
                "showProgress" -> binding.progressBar.visibility = VISIBLE
                "error" -> binding.progressBar.visibility = GONE
                "success" -> {
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.try1, R.anim.try2)
                }
            }

        }
    }
}