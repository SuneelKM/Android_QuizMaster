package com.example.quizmaster.ui.view

import android.app.AlertDialog
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val vm: AuthViewModel by viewModels()

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
                    resources.getString(R.string.emailError),
                    Toast.LENGTH_LONG).show()

            } else vm.handleResetPassword(email)

        }

        binding.languageSignIn.setOnClickListener {
            vm.setLanguage(baseContext)
            recreate()
        }

// Login
        binding.signInBtn.setOnClickListener {
            email = binding.emailEt.text.toString().trim()
            val pass = binding.passET.text.toString()

            if (email.isEmpty()) {
                binding.emailEt.error = resources.getString(R.string.emailError)
            } else if (pass.isEmpty()) {
                binding.passET.error = resources.getString(R.string.passwordError)
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
                    finish()
                }
                "error" -> alertDialog(resources.getString(R.string.error))
                "not found" -> alertDialog(resources.getString(R.string.notFound))
                "email sent" -> alertDialog("${resources.getString(R.string.emailSend)} $email")
            }

        }
    }

    override fun onStart() {
        super.onStart()
            if (vm.isEmailVerified()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
    }

    private fun alertDialog(message:String){
        binding.progressBar.visibility = GONE
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setCancelable(true)
        builder.setNegativeButton(resources.getString(R.string.ok)) { dialog, _ -> dialog.cancel() }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}
