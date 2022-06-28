package com.example.quizmaster.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizmaster.data.model.UserData.UserSignup
import com.example.quizmaster.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.quizmaster.R


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
            onBackPressed()
            overridePendingTransition(R.anim.slide_left, R.anim.slide_left)

        }
        binding.button.setOnClickListener {
            val userName = binding.userEt.text.toString()
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()
            val termsCondition = binding.checkBox.isChecked

            if(userName.isEmpty()){
                binding.userEt.error = "Please enter a username"
            }
            else if (email.isEmpty())
                binding.emailEt.error = "Please enter a valid email"
            else if (pass.isEmpty())
                binding.passET.error = "Please enter a valid password"
            else if (confirmPass.isEmpty())
                binding.confirmPassEt.error = "Please enter a valid password"
            else if (!termsCondition)
                binding.checkBox.error = "Please accept the terms and conditions"
            else {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
//                                val user = firebaseAuth.currentUser
                                firebaseAuth.currentUser!!.sendEmailVerification()
                                Toast.makeText(this, "Check your email address", Toast.LENGTH_LONG).show()

                                val user = UserSignup(userName, email)
                                saveToDatabase(user)

                                val intent = Intent(this, SignInActivity::class.java)
                                startActivity(intent)

                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                this,
                                "${it.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                } else {
                    Toast.makeText(this, "Passwords does not match", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun saveToDatabase(user:UserSignup){
        val database = Firebase.database
        val uid = firebaseAuth.uid
        val myRef = database.getReference("/Users/$uid")
        myRef.setValue(user)

    }
}