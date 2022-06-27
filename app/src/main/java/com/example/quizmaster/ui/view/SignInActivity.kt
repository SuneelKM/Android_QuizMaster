package com.example.quizmaster.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizmaster.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

                if (email.isEmpty())
                    binding.emailEt.error = "Please enter a valid email"
                else if (pass.isEmpty())
                    binding.passET.error = "Please enter a valid password"

           else {

                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val emailVerified = firebaseAuth.currentUser!!.isEmailVerified
                            if(emailVerified){
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                            else{
                                Toast.makeText(this, "Please verify your email address", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "${it.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val emailVerified = firebaseAuth.currentUser!!.isEmailVerified
            if(emailVerified){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

}