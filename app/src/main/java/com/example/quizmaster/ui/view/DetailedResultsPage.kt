package com.example.quizmaster.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizmaster.R
import com.example.quizmaster.data.model.SubmittedQuestions
import com.example.quizmaster.databinding.ActivityDetailedResultsPageBinding
import com.example.quizmaster.ui.adapter.HistoryAdapter
import com.example.quizmaster.ui.adapter.ResultAdapter
import com.example.quizmaster.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedResultsPage : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedResultsPageBinding
    var submittedQuestions = ArrayList<SubmittedQuestions>()
    lateinit var adapter: ResultAdapter
    private val userVM: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedResultsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ResultAdapter(submittedQuestions, this)

        binding.homeBtn.setOnClickListener{
            var mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }

        binding.resultRecycler.layoutManager = LinearLayoutManager(this)
        binding.resultRecycler.adapter = adapter

        submittedQuestions.addAll(intent.extras?.get("submittedQuestions") as List<SubmittedQuestions>)

        adapter.setItems(submittedQuestions)

        getUserName()

    }



    private fun getUserName(){
        var username = userVM.username
        username.observe(this){
            binding.userNameR.text = it
        }

    }


}