package com.example.quizmaster

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quizmaster.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var reset_pwd: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        reset_pwd = findViewById(R.id.reset_pwd)
        reset_pwd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val email = binding.emailEt.text.toString()

                if (email.toString().isNotEmpty()) {

                    firebaseAuth.sendPasswordResetEmail(email.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val builder = AlertDialog.Builder(this@SignInActivity)
                                builder.setMessage("Email Sent to ${email.toString()}")
                                builder.setCancelable(true)
                                builder.setNegativeButton("OK", DialogInterface.OnClickListener
                                { dialog, which -> dialog.cancel() })
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.show()

                                Log.d(ContentValues.TAG, "Email sent.")
                            }
                            startActivity(Intent(this@SignInActivity, SignInActivity::class.java))

                        }
                }
            }
        }

        binding.textView.setOnClickListener {
//            val intent1 = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))


        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
//                            val intent = Intent(this, MainActivity::class.java)
//                            startActivity(intent)
                            startActivity(Intent(this@SignInActivity, MainActivity::class.java))

                        } else {
//                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this@SignInActivity,
                            "Failed to create user:${it.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            } else {
                Toast.makeText(this@SignInActivity, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
}