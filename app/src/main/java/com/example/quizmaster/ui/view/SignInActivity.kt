package com.example.quizmaster.ui.view

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizmaster.data.model.UserData.UserSignup
import com.example.quizmaster.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.lang.Thread.sleep


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }

// Reset password

        binding.resetPwd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val emailReset = binding.emailEt.text.toString().trim()

                if (emailReset.isEmpty() ) {

                    Toast.makeText(
                        this@SignInActivity,
                        "Please enter your email ",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                else if(emailExist(emailReset)==true){

                    println("emailEt.text.toString()")
                    firebaseAuth.sendPasswordResetEmail(emailReset)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val builder = AlertDialog.Builder(this@SignInActivity)
                                builder.setMessage("Email Sent to ${emailReset}")
                                builder.setCancelable(true)
                                builder.setNegativeButton("OK", DialogInterface.OnClickListener
                                { dialog, which -> dialog.cancel() })
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.show()

                                Log.d(ContentValues.TAG, "Email sent.")

                                Toast.makeText(
                                    this@SignInActivity,
                                    "check your email ",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            else{

                                val builder = AlertDialog.Builder(this@SignInActivity)
                                builder.setMessage("Sorry, we could not find your account")
                                builder.setCancelable(true)
                                builder.setNegativeButton("OK", DialogInterface.OnClickListener
                                { dialog, which -> dialog.cancel() })
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.show()

                                Toast.makeText(
                                    this@SignInActivity,
                                    "Please enter registered email id",
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }.addOnFailureListener {
                            // Use Timber Log  here
//                            Toast.makeText(
//                                this@SignInActivity,
//                                "${it.message}",
//                                Toast.LENGTH_LONG
//                            ).show()
                        }
                    }
            }

        })

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val pass = binding.passET.text.toString()

            if (email.isEmpty()) {
                binding.emailEt.error = "Please enter a valid email"
            } else if (pass.isEmpty()) {
                binding.passET.error = "Please enter a valid password"
            } else {

                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val emailVerified = firebaseAuth.currentUser!!.isEmailVerified
                            if (emailVerified) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Please verify your email address",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    } .addOnFailureListener {
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
            if (emailVerified) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }

        fun emailExist(email: String): Boolean {
            var out1: Boolean
            out1 = true
            FirebaseAuth
                .getInstance()
                .fetchSignInMethodsForEmail(email)
                .addOnCompleteListener { task ->

                    //         Log.d("Line 125", "" + task.result?.signInMethods?.size)

                    if (task.isSuccessful) {
                        if (task.result?.signInMethods?.size === 0) {

                            // email not existed
                            Log.d("Line 129", "email  not existed")
                            out1 = true
                        } else {
                            // email existed
                            Log.d("Line 135", "checkEmailExists: ")
                            out1 = false
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        this,
                        "${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            return out1

        }


}
