package com.example.quizmaster.ui.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.quizmaster.R
import com.example.quizmaster.databinding.ActivitySignInBinding
import com.example.quizmaster.ui.viewmodel.AuthViewModel
import timber.log.Timber
import com.google.firebase.database.*

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    val vm: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var email = binding.emailEt.text.toString().trim()

        binding.signUpText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_up)

        }

// Reset password
        binding.resetPwd.setOnClickListener {
            email = binding.emailEt.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(
                    this@SignInActivity,
                    "Please enter your email ",
                    Toast.LENGTH_LONG).show()

            } else vm.handleResetPassword(email)

        }

// Login
        binding.signInBtn.setOnClickListener {
            email = binding.emailEt.text.toString().trim()
            val pass = binding.passET.text.toString()

            if (email.isEmpty()) {
                binding.emailEt.error = "Please enter a valid email"
            } else if (pass.isEmpty()) {
                binding.passET.error = "Please enter a valid password"
            } else
                vm.handleSignIn(email, pass)

        }

        vm.signInState.observe(this){

            when (it) {
                "showProgress" -> binding.progressBar.visibility = VISIBLE
                "hideProgress" -> binding.progressBar.visibility = GONE
                "success" -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left)
                }
                "error" -> alertDialog("Something went wrong")
                "not found" -> alertDialog("Sorry, we could not find your account")
                "email sent" -> alertDialog("Email sent to $email")
            }

        }

    }

    override fun onStart() {
        super.onStart()
            if (vm.isEmailVerified()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

    }

    private fun alertDialog(message:String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setCancelable(true)
        builder.setNegativeButton("OK", DialogInterface.OnClickListener
        { dialog, _ -> dialog.cancel() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


}
